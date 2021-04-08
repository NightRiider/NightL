package me.jpalip.interpret.primitive;

public class IntPrimitive extends Primitive<Integer> {

    public IntPrimitive(int value) {
        super(value, Type.INT);
    }

    @Override
    public Primitive<?> add(Primitive<?> other) {
        if (other.getType() != Type.INT) {
            throw new RuntimeError("Cannot add integer and float");
        }

        IntPrimitive intValue = other.getValue(Type.INT);

        return new IntPrimitive(value + intValue.getValue());
    }

    @Override
    public Primitive<?> min(Primitive<?> other) {
        if (other.getType() != Type.INT) {
            throw new RuntimeError("Cannot subtract integer and float");
        }

        IntPrimitive intValue = other.getValue(Type.INT);

        return new IntPrimitive(value - intValue.getValue());
    }

    @Override
    public Primitive<?> div(Primitive<?> other) {
        if (other.getType() != Type.INT) {
            throw new RuntimeError("Cannot divide integer and float");
        }

        IntPrimitive intValue = other.getValue(Type.INT);

        return new IntPrimitive(value / intValue.getValue());
    }

    @Override
    public Primitive<?> mul(Primitive<?> other) {
        if (other.getType() != Type.INT) {
            throw new RuntimeError("Cannot multiply integer and float");
        }

        IntPrimitive intValue = other.getValue(Type.INT);

        return new IntPrimitive(value * intValue.getValue());
    }
}
