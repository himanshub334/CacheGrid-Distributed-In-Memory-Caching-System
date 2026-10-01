#include <stdint.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    uint8_t *data;
    size_t capacity;
} cache_buffer;

cache_buffer *cache_buffer_create(size_t capacity) {
    cache_buffer *b = malloc(sizeof(cache_buffer));
    if (!b) return NULL;
    b->data = malloc(capacity);
    if (!b->data) { free(b); return NULL; }
    b->capacity = capacity;
    memset(b->data, 0, capacity);
    return b;
}

void cache_buffer_destroy(cache_buffer *b) {
    if (!b) return;
    free(b->data);
    free(b);
}
