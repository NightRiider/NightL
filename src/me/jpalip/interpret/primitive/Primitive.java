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

    public BooleanPrimitive less(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public BooleanPrimitive lessEqual(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public BooleanPrimitive greater(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public BooleanPrimitive greaterEqual(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public BooleanPrimitive equal(Primitive<?> other)
    {
        return new BooleanPrimitive(this.equals(other));
    }

    public BooleanPrimitive notEqual(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public BooleanPrimitive and(Primitive<?> other)
    {
        throw new RuntimeError("Cannot use 'and' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public BooleanPrimitive or(Primitive<?> other)
    {
        throw new RuntimeError("Cannot use 'or' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }

    public BooleanPrimitive not()
    {
        throw new RuntimeError("Cannot use 'not' on " + getClass().getSimpleName() + "");
    }

    public boolean isTrue()
    {
        throw new RuntimeError("Cannot check if " + getClass().getSimpleName() + " is true");
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