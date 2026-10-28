# Leve2 — arquitetura de baixo desempenho

## Camadas

- **Núcleo ARMSX2:** emulação PS2, JIT ARM64, renderização, áudio e entrada.
- **Android existente:** biblioteca de jogos, tela de configurações, AutoTune e suporte a drivers.
- **Leve2:** classificação explicável, recomendações, catálogo de presets e diagnóstico.
- **Comunidade:** resultados de testes versionados por aparelho e por jogo.

## Regras de segurança de desempenho

A recomendação nunca deve reduzir a configuração de um aparelho apenas porque uma leitura falhou. O usuário sempre pode desfazer o preset. Toda mudança experimental precisa ter indicação de risco e ser aplicável por jogo.

O benchmark deve registrar frame time e não apenas FPS. Um resultado de 30 FPS com frame time estável é preferível a picos de 45 FPS com travamentos. Temperatura e duração do teste também fazem parte do resultado.

## Licença e distribuição

O código derivado permanece sob GPLv3. BIOS e jogos não são distribuídos pelo projeto. A identidade Leve2 deve manter os créditos do ARMSX2 e do PCSX2 e informar claramente as modificações realizadas.
