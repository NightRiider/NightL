package me.jpalip.interpret.primitive;

public class FloatPrimitive extends Primitive<Float> {

    public FloatPrimitive(float value) {
        super(value, Type.FLOAT);
    }

    @Override
    public Primitive<?> add(Primitive<?> other) {
        if (other.getType() != Type.FLOAT) {
            throw new RuntimeError("Cannot add integer and float");
        }

        FloatPrimitive floatValue = other.getValue(Type.FLOAT);

        return new FloatPrimitive(value + floatValue.getValue());
    }

    @Override
    public Primitive<?> min(Primitive<?> other) {
        if (other.getType() != Type.FLOAT) {
            throw new RuntimeError("Cannot subtract integer and float");
        }

        FloatPrimitive floatValue = other.getValue(Type.FLOAT);

        return new FloatPrimitive(value - floatValue.getValue());
    }

    @Override
    public Primitive<?> div(Primitive<?> other) {
        if (other.getType() != Type.FLOAT) {
            throw new RuntimeError("Cannot divide integer and float");
        }

        FloatPrimitive floatValue = other.getValue(Type.FLOAT);

        return new FloatPrimitive(value / floatValue.getValue());
    }

    @Override
    public Primitive<?> mul(Primitive<?> other) {
        if (other.getType() != Type.FLOAT) {
            throw new RuntimeError("Cannot multiply integer and float");
        }

        FloatPrimitive floatValue = other.getValue(Type.FLOAT);

        return new FloatPrimitive(value * floatValue.getValue());
    }
}
