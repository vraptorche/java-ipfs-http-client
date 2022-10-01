package io.ipfs.api.cbor;

/*
 * JACOB - CBOR implementation in Java.
 *
 * (C) Copyright - 2013 - J.W. Janssen <j.w.janssen@lxtreme.nl>
 *
 * Licensed under Apache License v2.0.
 */

import static io.ipfs.api.cbor.CborConstants.*;

/**
 * Represents the various major types in CBOR, along with their .
 * <p>
 * The major type is encoded in the upper three bits of each initial byte. The lower 5 bytes represent any additional information.
 * </p>
 */
public class CborType {
    private final int major;
    private final int additional;

    private CborType(int major, int additional) {
        this.major = major;
        this.additional = additional;
    }

    /**
     * Returns a descriptive string for the given major type.
     *
     * @param mt the major type to return as string, values from [0..7] are supported.
     * @return the name of the given major type, as String, never <code>null</code>.
     * @throws IllegalArgumentException in case the given major type is not supported.
     */
    public static String getName(int mt) {
	    return switch (mt) {
		    case TYPE_ARRAY -> "array";
		    case TYPE_BYTE_STRING -> "byte string";
		    case TYPE_FLOAT_SIMPLE -> "float/simple value";
		    case TYPE_MAP -> "map";
		    case TYPE_NEGATIVE_INTEGER -> "negative integer";
		    case TYPE_TAG -> "tag";
		    case TYPE_TEXT_STRING -> "text string";
		    case TYPE_UNSIGNED_INTEGER -> "unsigned integer";
		    default -> throw new IllegalArgumentException("Invalid major type: " + mt);
	    };
    }

    /**
     * Decodes a given byte value to a {@link CborType} value.
     *
     * @param i the input byte (8-bit) to decode into a {@link CborType} instance.
     * @return a {@link CborType} instance, never <code>null</code>.
     */
    public static CborType valueOf(int i) {
        return new CborType((i & 0xff) >>> 5, i & 0x1f);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CborType other = (CborType) obj;
        return (major == other.major) && (additional == other.additional);
    }

    /**
     * @return the additional information of this type, as integer value from [0..31].
     */
    public int getAdditionalInfo() {
        return additional;
    }

    /**
     * @return the major type, as integer value from [0..7].
     */
    public int getMajorType() {
        return major;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + additional;
        result = prime * result + major;
        return result;
    }

    /**
     * @return <code>true</code> if this type allows for an infinite-length payload,
     *         <code>false</code> if only definite-length payloads are allowed.
     */
    public boolean isBreakAllowed() {
        return major == TYPE_ARRAY || major == TYPE_BYTE_STRING || major == TYPE_MAP
                || major == TYPE_TEXT_STRING;
    }

    /**
     * Determines whether the major type of a given {@link CborType} equals the major type of this {@link CborType}.
     *
     * @param other the {@link CborType} to compare against, cannot be <code>null</code>.
     * @return <code>true</code> if the given {@link CborType} is of the same major type as this {@link CborType}, <code>false</code> otherwise.
     * @throws IllegalArgumentException in case the given argument was <code>null</code>.
     */
    public boolean isEqualType(CborType other) {
        if (other == null) {
            throw new IllegalArgumentException("Parameter cannot be null!");
        }
        return major == other.major;
    }

    /**
     * Determines whether the major type of a given byte value (representing an encoded {@link CborType}) equals the major type of this {@link CborType}.
     *
     * @param encoded the encoded CBOR type to compare.
     * @return <code>true</code> if the given byte value represents the same major type as this {@link CborType}, <code>false</code> otherwise.
     */
    public boolean isEqualType(int encoded) {
        return major == ((encoded & 0xff) >>> 5);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName(major)).append('(').append(additional).append(')');
        return sb.toString();
    }
}