# Aprendizaje — Mini Tasks (sesión 3)

## Ciclo TDD: `GET /v1/tasks/{id}`

### Test que produjo el rojo

`TaskControllerV1GetByIdTest.getExistingTask`:

```java
mvc.perform(get("/v1/tasks/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Comprar leche"))
        .andExpect(jsonPath("$.done").value(false));
```

### Por qué falló

El `TaskControllerV1` solo tenía `list()` (`GET /v1/tasks`) y `create()` (`POST /v1/tasks`). No había ningún `@GetMapping("/{id}")`, así que la petición `GET /v1/tasks/1` la resolvía Spring como recurso estático inexistente y devolvía `404` (esperado `200`).

### Qué cambio lo hizo pasar

Añadí al `TaskControllerV1`:

```java
@GetMapping("/{id}")
public ResponseEntity<SimpleTask> get(@PathVariable("id") Long id) {
    return tasks.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
}
```

Con esto:
- `GET /v1/tasks/{id}` existente → `200` con el JSON de la tarea.
- `GET /v1/tasks/{id}` inexistente → `404` sin cuerpo (segundo test del mismo ciclo, que ya pasaba por casualidad pero ahora lo cubre la implementación real, no la ausencia de ruta).


