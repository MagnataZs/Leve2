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
) {
    enum class Name { ENTRY, BUDGET, STANDARD }

    enum class SuggestedPreset { LOW_END, FAST, OPTIMAL }

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
            val reasons = buildList {
                if (cores < 6) add("poucos núcleos disponíveis ($cores)")
                if (lowEnd) add("o aparelho foi sinalizado como entrada pelo Android")
            }

            return when {
                lowEnd && cores <= 4 -> Leve2DeviceProfile(
                    name = Name.ENTRY,
                    reasons = reasons,
                    suggestedPreset = SuggestedPreset.LOW_END,
                )
                lowEnd -> Leve2DeviceProfile(
                    name = Name.BUDGET,
                    reasons = reasons,
                    suggestedPreset = SuggestedPreset.FAST,
                )
                else -> Leve2DeviceProfile(
                    name = Name.STANDARD,
                    reasons = reasons,
                    suggestedPreset = SuggestedPreset.OPTIMAL,
                )
            }
        }
    }
}
