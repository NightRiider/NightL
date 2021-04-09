package me.jpalip.interpret.primitive;

import java.util.List;
import java.util.Objects;

import me.jpalip.lexerparser.nodes.Node;

public abstract class Primitive<T> {

    protected T value;
    protected Type type;

    public Primitive(T value, Type type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }


    public Primitive<?> add(Primitive<?> other) {
        throw new RuntimeError("Cannot add " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public Primitive<?> min(Primitive<?> other) {
        throw new RuntimeError("Cannot subtract " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public Primitive<?> div(Primitive<?> other) {
        throw new RuntimeError("Cannot divide " + getClass().getSimpleName() + " with " + other.getClass().getSimpleName());
    }

    public Primitive<?> mul(Primitive<?> other) {
        throw new RuntimeError("Cannot multiply " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public <CT extends Primitive<?>> CT getValue(Type type) {
        if (type != this.type) {
            throw new RuntimeError("Expected datatype " + type);
        }

        return (CT) this;
    }

    public String toString() {
        return value != null ? value.toString() : "void";
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Primitive<?> primitive = (Primitive<?>) o;
        return value.equals(primitive.value) && type == primitive.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}