# PetSalus - Plataforma Completa para Tutores de Pets

## Visão Geral
O **PetSalus** é uma plataforma inovadora que combina funcionalidades de **agenda, marketplace e rede social** para tutores de pets. Nosso objetivo é facilitar a rotina dos donos de animais, oferecendo soluções inteligentes para organização de consultas, contratação de serviços e interação entre tutores.

## Tecnologias Utilizadas
- **Backend**: Java 17 com Spring Boot 3.4.3
- **Banco de Dados**: PostgreSQL
- **Frontend**: Angular 17
- **Mobile**: React Native

## Funcionalidades Principais
- **Agenda Inteligente**: Notificação sobre vacinas, consultas e medicamentos.
- **Marketplace de Pet Sitters e Passeadores**: Avaliações verificadas e sistema seguro de contratação.
- **Rede Social para Donos de Pets**: Compartilhamento de fotos, dicas e organização de eventos.
- **Registro de Saúde**: Centraliza histórico veterinário.
- **Emergências Veterinárias**: Localização de clínicas 24h.
- **Assinatura de Produtos para Pets**: Compra recorrente de ração e petiscos.

## Instalação e Configuração
### Backend
1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/petsalus.git
   ```
2. Acesse a pasta do backend:
   ```sh
   cd petsalus/backend
   ```
3. Configure o banco de dados PostgreSQL.
4. Configure as variáveis de ambiente no `application.yml`.
5. Execute a aplicação:
   ```sh
   mvn spring-boot:run
   ```

### Frontend
1. Acesse a pasta do frontend:
   ```sh
   cd petsalus/frontend
   ```
2. Instale as dependências:
   ```sh
   npm install
   ```
3. Inicie a aplicação:
   ```sh
   ng serve
   ```

### Mobile
1. Acesse a pasta do app mobile:
   ```sh
   cd petsalus/mobile
   ```
2. Instale as dependências:
   ```sh
   npm install
   ```
3. Execute o app em um emulador ou dispositivo:
   ```sh
   npx react-native run-android  # Para Android
   npx react-native run-ios  # Para iOS
   ```