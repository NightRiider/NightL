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

    @Override
    public BooleanPrimitive less(Primitive<?> other)
    {
        if (other.type != Type.INT)
        {
            throw new RuntimeError("Right hand of operation '<' must be a number");
        }

        IntPrimitive value = other.getValue(Type.INT);
        return new BooleanPrimitive(this.value < value.getValue());
    }

    @Override
    public BooleanPrimitive lessEqual(Primitive<?> other)
    {
        if (other.type != Type.INT)
        {
            throw new RuntimeError("Right hand of operation '<=' must be a number");
        }

        IntPrimitive value = other.getValue(Type.INT);
        return new BooleanPrimitive(this.value <= value.getValue());
    }

    @Override
    public BooleanPrimitive greater(Primitive<?> other)
    {
        if (other.type != Type.INT)
        {
            throw new RuntimeError("Right hand of operation '>' must be a number");
        }

        IntPrimitive value = other.getValue(Type.INT);
        return new BooleanPrimitive(this.value > value.getValue());
    }

    @Override
    public BooleanPrimitive greaterEqual(Primitive<?> other)
    {
        if (other.type != Type.INT)
        {
            throw new RuntimeError("Right hand of operation '>=' must be a number");
        }

        IntPrimitive value = other.getValue(Type.INT);
        return new BooleanPrimitive(this.value >= value.getValue());
    }

    @Override
    public BooleanPrimitive notEqual(Primitive<?> other)
    {
        return equal(other).not();
    }

}
