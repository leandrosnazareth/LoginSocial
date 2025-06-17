🔐 Sistema de Login Social com Spring Boot
==========================================

🌟 Visão Geral
--------------

Projeto completo de autenticação social com Spring Boot que permite login via Google e Facebook, com persistência automática de usuários e interface moderna.

✨ Funcionalidades Principais
----------------------------

### 🔑 Autenticação

*   Login com Google OAuth2
    
*   Login com Facebook OAuth2
    
*   Logout seguro com CSRF protection
    
*   Persistência automática de novos usuários
    

### 🛡️ Segurança

*   Proteção contra CSRF
    
*   Sessões gerenciadas pelo Spring Security
    
*   Dados sensíveis protegidos
    

### 💻 Frontend

*   Interface responsiva com Bootstrap 5
    
*   Páginas Thymeleaf dinâmicas
    
*   Feedback visual para usuários
    

🛠 Stack Tecnológica
--------------------

ComponenteVersãoFinalidadeSpring Boot3.2.0Framework backendSpring Security6.1.0Autenticação e autorizaçãoThymeleaf3.1.1Templates HTMLH2 Database2.1.214Banco de dados em memóriaBootstrap5.3.0Framework CSSOAuth2 Client6.1.0Integração com login social

🚀 Começando
------------

### Pré-requisitos

*   JDK 17+
    
*   Maven 3.9+
    
*   Contas de desenvolvedor:
    
    *   [Google Cloud Console](https://console.cloud.google.com/)
        
    *   [Facebook Developers](https://developers.facebook.com/)
        

### Instalação

1.  Clone o repositório:
    
2.  Configure as credenciais OAuth2:
    
3.  Execute a aplicação:
    
4.  Acesse no navegador:
    

🏗️ Estrutura do Projeto
------------------------

src/

├── main/
│ ├── java/
│ │ └── com/example/
│ │ ├── config/ # Configurações de segurança
│ │ ├── controller/ # Controladores MVC
│ │ ├── entity/ # Entidades JPA
│ │ ├── repository/ # Repositórios Spring Data
│ │ ├── service/ # Lógica de negócio
│ │ └── Application.java
│ └── resources/
│ ├── static/ # Assets (CSS, JS, imagens)
│ ├── templates/ # Páginas Thymeleaf
│ └── application.properties

📚 Documentação Adicional
-------------------------

### Configuração OAuth2

*   [Google OAuth Setup Guide](https://developers.google.com/identity/protocols/oauth2)
    
*   [Facebook Login Documentation](https://developers.facebook.com/docs/facebook-login)
    

### Fluxo de Autenticação

📄 Licença
----------

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](https://LICENSE) para detalhes.

✉️ Contato
----------

**Leandro Nazareth**

[https://img.shields.io/badge/GitHub-Repositório-blue?logo=github](https://img.shields.io/badge/GitHub-Repositório-blue?logo=github)[https://img.shields.io/badge/LinkedIn-Perfil-blue?logo=linkedin](https://img.shields.io/badge/LinkedIn-Perfil-blue?logo=linkedin)📧 [leandro.nazareth@gmail.com](https://mailto:leandro.nazareth@gmail.com)

🤝 Como Contribuir
------------------

1.  Faça um fork do projeto
    
2.  Crie uma branch para sua feature (git checkout -b feature/incrivel)
    
3.  Commit suas mudanças (git commit -am 'Adiciona feature incrível')
    
4.  Push para a branch (git push origin feature/incrivel)
    
5.  Abra um Pull Request