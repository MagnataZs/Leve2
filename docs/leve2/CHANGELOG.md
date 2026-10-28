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

## Próxima etapa

Adicionar a aplicação do preset recomendado ao finalizar o onboarding, sempre com confirmação do usuário, e gerar um relatório exportável com modelo do aparelho, perfil escolhido, jogo, renderer, resolução, frame time e temperatura.
