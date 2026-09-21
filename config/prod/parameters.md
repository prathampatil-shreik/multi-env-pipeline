# Production – Configuration Parameters

These parameters are injected into the ECS task definition for the Production environment.

| Parameter | Value |
|---|---|
| `APP_ENV` | `PRODUCTION` |
| `APP_VERSION` | `${GIT_SHA}` (promoted from Staging) |
| `APP_MESSAGE` | `Welcome to Production` |
| `SERVER_PORT` | `8080` |
