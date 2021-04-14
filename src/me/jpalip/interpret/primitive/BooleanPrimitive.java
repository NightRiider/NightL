package me.jpalip.interpret.primitive;

public class BooleanPrimitive extends Primitive<Boolean> {

    public BooleanPrimitive(Boolean value)
    {
        super(value, Type.BOOLEAN);
    }

    @Override
    public boolean isTrue()
    {
        return value;
    }

    @Override
    public BooleanPrimitive not()
    {
        return new BooleanPrimitive(!value);
    }

    @Override
    public BooleanPrimitive and(Primitive<?> other)
    {
        if (other.type != Type.BOOLEAN)
        {
            throw new RuntimeError("Right hand of operation 'and' must be a boolean");
        }

        BooleanPrimitive value = other.getValue(Type.BOOLEAN);

        return new BooleanPrimitive(this.value && value.getValue());
    }

    @Override
    public BooleanPrimitive or(Primitive<?> other)
    {
        if (other.type != Type.BOOLEAN)
        {
            throw new RuntimeError("Right hand of operation 'or' must be a boolean");
        }

        BooleanPrimitive value = other.getValue(Type.BOOLEAN);

        return new BooleanPrimitive(this.value || value.getValue());
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        if (other.type != Type.STRING)
        {
            throw new RuntimeError("Right hand of operation '+' must be a string");
        }

        StringPrimitive value = other.getValue(Type.STRING);

        return new StringPrimitive(this.value + value.getValue());
    }
}