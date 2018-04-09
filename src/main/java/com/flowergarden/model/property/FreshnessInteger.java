package com.flowergarden.model.property;

import lombok.Data;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Data
public class FreshnessInteger implements Freshness<Integer>, UserType {

    private Integer freshness;

    @Override
    public void reduce() throws UnsupportedOperationException {
        if (freshness > 0)
            freshness -= 1;
        else
            throw new UnsupportedOperationException("Сan not reduce the freshness 0");
    }

    @Override
    public int compareTo(Freshness o) {
        if (!(o instanceof FreshnessInteger))
            throw new UnsupportedOperationException();

        FreshnessInteger fio = (FreshnessInteger) o;
        if (freshness > fio.getFreshness()) return 1;
        if (freshness < fio.getFreshness()) return -1;
        return 0;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] {Types.INTEGER};
    }

    @Override
    public Class returnedClass() {
        return FreshnessInteger.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
//        return !(x == null || y == null) && x.equals(y);
        // TODO
        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        assert (x != null);
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        final Integer freshness = rs.getInt(names[0]);
        FreshnessInteger freshnessInteger = new FreshnessInteger();
        freshnessInteger.setFreshness(freshness);
        return freshnessInteger;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        st.setInt(index, ((FreshnessInteger) value).freshness);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
