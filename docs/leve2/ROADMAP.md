# Leve2 — roadmap inicial

Leve2 é a camada/distribuição comunitária baseada no ARMSX2 para priorizar jogabilidade em aparelhos Android de entrada.

## Baseline confirmado

O ARMSX2 já fornece o núcleo ARM64, o aplicativo Android, o preset Low-End, AutoTune por jogo, classificação básica do aparelho e suporte a drivers/renderers para GPUs Adreno e Mali. Leve2 deve aproveitar esses recursos e concentrar seu trabalho em decisões orientadas por dados, explicações claras e perfis reproduzíveis.

## Primeira entrega

1. Perfil de dispositivo explicável, sem impor configurações ao usuário.
2. Recomendação inicial com motivo visível: RAM, núcleos disponíveis e sinal de aparelho low-RAM.
3. Presets nomeados para aparelhos de entrada, com versão e origem documentadas.
4. Relatório de diagnóstico exportável para a comunidade.
5. Matriz de testes por aparelho, jogo, renderer, resolução, temperatura e frame time.

## Fora da primeira entrega

- Distribuição de BIOS, jogos ou qualquer conteúdo protegido.
- Promessa de compatibilidade universal.
- Alterações agressivas no JIT sem benchmark e teste de regressão.
- Coleta obrigatória de dados pessoais ou telemetria remota.

## Critério de sucesso

Um usuário com um aparelho de entrada deve conseguir escolher um perfil, entender o que foi alterado e repetir o teste sem depender de conhecimento avançado de emulação.
