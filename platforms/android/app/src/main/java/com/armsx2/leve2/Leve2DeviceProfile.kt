package com.armsx2.leve2

import android.content.Context
import com.armsx2.DeviceTier

/**
 * User-facing explanation of the first-run recommendation.
 *
 * This is deliberately a recommendation model, not a hard performance cap. The existing
 * ARMSX2 settings and Low-End preset remain the source of truth for actual emulation options.
 */
data class Leve2DeviceProfile(
    val name: Name,
    val reasons: List<String>,
    val suggestedPreset: SuggestedPreset,
    val legacyAdrenoHint: Boolean = false,
) {
    enum class Name { ENTRY, BUDGET, STANDARD }

    enum class SuggestedPreset { LOW_END, FAST, ADRENO_LEGACY, OPTIMAL }

    fun summary(): String = buildString {
        append(name.name.lowercase().replace('_', '-'))
        append(" / ")
        append(suggestedPreset.name.lowercase().replace('_', '-'))
        if (reasons.isNotEmpty()) {
            append(" — ")
            append(reasons.joinToString(", "))
        }
    }

    companion object {
        /**
         * Conservative and explainable. A failed probe in DeviceTier is designed not to hobble
         * capable devices, so this method also avoids turning uncertainty into a low-end verdict.
         */
        fun recommend(context: Context): Leve2DeviceProfile {
            val cores = DeviceTier.coreCount()
            val lowEnd = DeviceTier.isLowEnd(context)
            val legacyAdreno = DeviceTier.hasLegacyAdrenoHint()
            val reasons = buildList {
                if (cores < 6) add("poucos núcleos disponíveis ($cores)")
                if (lowEnd) add("o aparelho foi sinalizado como entrada pelo Android")
                if (legacyAdreno) add("perfil Qualcomm/Adreno antiga detectado")
            }

            return when {
                lowEnd && cores <= 4 -> Leve2DeviceProfile(
                    name = Name.ENTRY,
                    reasons = reasons,
                    suggestedPreset = SuggestedPreset.LOW_END,
                    legacyAdrenoHint = legacyAdreno,
                )
                legacyAdreno -> Leve2DeviceProfile(
                    name = Name.BUDGET,
                    reasons = reasons,
                    suggestedPreset = SuggestedPreset.ADRENO_LEGACY,
                    legacyAdrenoHint = true,
                )
                lowEnd -> Leve2DeviceProfile(
                    name = Name.BUDGET,
                    reasons = reasons,
                    suggestedPreset = SuggestedPreset.FAST,
                    legacyAdrenoHint = false,
                )
                else -> Leve2DeviceProfile(
                    name = Name.STANDARD,
                    reasons = reasons,
                    suggestedPreset = SuggestedPreset.OPTIMAL,
                    legacyAdrenoHint = false,
                )
            }
        }
    }
}
