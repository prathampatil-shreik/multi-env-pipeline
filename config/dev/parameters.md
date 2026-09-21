# Dev – Configuration Parameters

These parameters are injected into the ECS task definition for the Dev environment.
Do NOT store actual secrets here. Use AWS Secrets Manager or Parameter Store for sensitive values.

| Parameter | Value |
|---|---|
| `APP_ENV` | `DEV` |
| `APP_VERSION` | `${GIT_SHA}` (set by pipeline) |
| `APP_MESSAGE` | `Welcome to Development` |
| `SERVER_PORT` | `8080` |
