🍽️ MagicFridgeIA

API backend desenvolvida em Java com Spring Boot, que integra IA generativa (Gemini API) para sugerir receitas com base em ingredientes comuns, aplicando boas práticas de arquitetura, segurança, organização de código e programação reativa com WebFlux.
Projeto desenvolvido durante o curso Batismo de Java (Fiasco/Horace Mota), com diversas melhorias e extensões além do escopo original.

🚀 Tecnologias Utilizadas:

-> Java 17+
-> Spring Boot
-> Spring WebFlux
-> WebClient
-> Spring Data JPA
-> PostgreSQL
-> Flyway
-> Gemini API (IA Generativa)
-> Git & GitHub

🧠 Funcionalidades:

-> CRUD completo de alimentos
-> Persistência com PostgreSQL
-> Versionamento de banco com Flyway
-> Arquitetura REST organizada
-> Integração com IA generativa para sugestão de receitas
-> Programação reativa usando WebFlux
-> Tratamento avançado de erros HTTP
-> Segurança via variáveis de ambiente

🏗️ Arquitetura do Projeto:

Estrutura baseada em boas práticas de separação de responsabilidades:

controller  → Camada de entrada REST  
service     → Regras de negócio  
repository  → Acesso ao banco  
model        → Entidades e domínio  
config       → Configurações gerais (WebClient, etc)

🤖 Integração com IA (Gemini API)

A integração com IA foi feita utilizando Spring WebClient + WebFlux, permitindo comunicação reativa e não-bloqueante com a API do Gemini.

-> Principais pontos técnicos:
-> Autenticação via header x-goog-api-key
-> Uso de system_instruction para definir comportamento do modelo
-> Payload estruturado em contents → parts → text
-> Parsing da resposta: candidates → content → parts → text
-> Tratamento de erros 4xx com .onStatus() para captura e log detalhado

🔐 Segurança

Uso de variáveis de ambiente para dados sensíveis:

-> URL do banco
-> Usuário
-> Senha
-> API Key
-> Nenhuma credencial sensível é versionada no repositório.

🛠️ Configuração do Ambiente

1. Variáveis de ambiente
Configure as seguintes variáveis no seu sistema:

-> API_KEY=sua_api_key_do_gemini

2. application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/magicfridge
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=none
spring.flyway.enabled=true

gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
gemini.api.key=${API_KEY}

▶️ Executando o Projeto

-> ./mvnw spring-boot:run

A API estará disponível em:

-> http://localhost:8080
