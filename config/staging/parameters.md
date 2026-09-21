# Staging – Configuration Parameters

These parameters are injected into the ECS task definition for the Staging environment.

| Parameter | Value |
|---|---|
| `APP_ENV` | `STAGING` |
| `APP_VERSION` | `${GIT_SHA}` (promoted from Dev) |
| `APP_MESSAGE` | `Welcome to Staging` |
| `SERVER_PORT` | `8080` |
