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



## Sesion 4 - Servicio y normalizacion del titulo

### Red

TaskServiceTest.createStripsTitleEdges:

- repository.save devuelve su argumento.
- createTask(new CreateTaskDTO("  Repasar Spring Boot  "))
- Se esperaba titulo "Repasar Spring Boot" y done=false.
- Fallo porque createTask guardaba dto.title() sin normalizar.

### Cambio

createTask ahora usa dto.title().strip() antes de crear SimpleTask.

### Verde

- TaskServiceTest pasa (2 tests).
- Se extrajo TaskService + TaskRepository/InMemoryTaskRepository.
- TaskControllerV1 y TaskControllerV2 delegan en TaskService.
- Las pruebas MVC se actualizaron para mockear TaskService.
- mvn test queda en verde (9 tests).

## Sesion 4b - Plan pendiente (no implementado todav�a)

Idea para el proximo avance:

- Operacion idempotente para completar una tarea: completeTask(Long id).
- Tests previstos:
  - completeMarksTaskDone: la tarea queda con done=true.
  - completeTwiceKeepsTaskDone: repetir la operaci�n no la desmarca.
- Implementacion en TaskService, probada con repositorio mock.
- Conservar titulo e id.
- Conectar a un endpoint propio, por ejemplo PATCH /v4/tasks/{id}/complete.
- Probar recurso inexistente y su 404.
