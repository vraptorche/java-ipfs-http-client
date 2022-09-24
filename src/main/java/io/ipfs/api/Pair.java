package io.ipfs.api;

import java.util.function.Function;

public record Pair<L, R>(L left, R right) {

	public Pair<R, L> swapped() {
		return new Pair<>(right, left);
	}

	public <B, D> Pair<B, D> apply(Function<L, B> applyLeft, Function<R, D> applyRight) {
		return new Pair<>(
				applyLeft.apply(left),
				applyRight.apply(right));
	}
}