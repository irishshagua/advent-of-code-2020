package io.github.irishshagua.app.io;

public interface Converter<FROM, TO> {

    TO convert(FROM from);
}
