# Leve2 — log de modificações

## 2026-10-28 — primeira integração

### Base utilizada

- Repositório upstream: `ARMSX2/ARMSX2`.
- Branch de trabalho: `leve2-low-end`.
- Núcleo preservado: ARMSX2/PCSX2, sem alteração de JIT ou renderer nesta etapa.
- Plataforma inicial: Android ARM64.

### Modificações do Leve2

1. Criado `Leve2DeviceProfile`, um modelo de recomendação que usa a classificação defensiva já existente em `DeviceTier`.
2. A recomendação diferencia `entry`, `budget` e `standard`.
3. Cada recomendação inclui motivos legíveis, sem transformar falha de leitura em punição de desempenho.
4. Integrado um cartão Leve2 no onboarding, na página de desempenho.
5. O cartão mostra o perfil sugerido, os motivos e informa que a escolha pode ser alterada por jogo.
6. Adicionadas strings padrão e tradução inicial em português brasileiro.
7. Adicionado o nome de aplicativo `Leve2` para builds publicadas pelo workflow.
8. Adicionado `.github/workflows/leve2-android.yml`.

### Build e publicação

A Action instala Java 17, Android SDK, NDK 29, CMake, dependências do shaderc e o target Rust ARM64. Depois gera um APK GitHub-flavor ARM64, publica o artefato por 30 dias e, quando acionada por uma tag `v*` ou `leve2-v*`, anexa o APK a uma release prévia.

O APK não contém BIOS, ROMs ou ISOs. O usuário precisa fornecer uma BIOS obtida do próprio console e os jogos que possui.

### Validação

- `git diff --check`: passou.
- JSON `pt-BR.json`: válido.
- Compilação local: não concluída porque o Sandbox não possui Android SDK/NDK configurados (`SDK location not found`).
- A compilação reproduzível está configurada para ocorrer no GitHub Actions.

### Correção de CI após o primeiro push

O primeiro workflow parou ao tentar instalar `platforms;android-37`, pacote que não estava disponível no runner. O build agora usa Android API 36 por meio da propriedade `armsx2.compileSdk`, mantendo essa escolha explícita e reproduzível no workflow.

O segundo workflow confirmou a compilação nativa, mas falhou no AAR metadata porque AndroidX 1.19/2.11 exigia API 37. As versões AndroidX/Compose foram fixadas em uma linha compatível com API 36. Os WAVs de menu, que estavam presentes no working tree mas ignorados pelo snapshot Git, também foram adicionados explicitamente ao repositório.

## 2026-10-28 — compatibilidade Adreno antiga

- Adicionada uma detecção heurística e não invasiva para Snapdragon 680/662 (`SM6225`/`SM6115`) e variantes Qualcomm do Redmi Note 11.
- Criado o preset **Adreno antiga**, que usa resolução interna nativa, escala de apresentação 1x, blending mínimo, mipmaps desligados, pré-carga parcial e o perfil de recursos Adreno já existente.
- O preset não força Vulkan, OpenGL, driver de terceiros ou skip-draw: cada jogo continua podendo ser ajustado manualmente.
- O onboarding passa a explicar quando o aparelho foi identificado como Qualcomm/Adreno antiga.

## Próxima etapa

Adicionar a aplicação do preset recomendado ao finalizar o onboarding, sempre com confirmação do usuário, e gerar um relatório exportável com modelo do aparelho, perfil escolhido, jogo, renderer, resolução, frame time e temperatura.
