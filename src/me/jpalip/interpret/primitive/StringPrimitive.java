package me.jpalip.interpret.primitive;

public class StringPrimitive extends Primitive<String> {

    public StringPrimitive(String value)
    {
        super(value, Type.STRING);
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        if (other.type == Type.STRING)
        {
            StringPrimitive value = other.getValue(Type.STRING);
            return new StringPrimitive(this.value + value.getValue());
        }

        throw new RuntimeError("Cannot concatenate " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
    }
}