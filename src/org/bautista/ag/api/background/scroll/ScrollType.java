package org.bautista.ag.api.background.scroll;

public enum ScrollType {

	EAST(ScrollDirection.EAST), WEST(ScrollDirection.WEST), NORTH(ScrollDirection.NORTH), SOUTH(
			ScrollDirection.SOUTH), HORIZONTAL(ScrollDirection.EAST, ScrollDirection.WEST), VERTICAL(
					ScrollDirection.NORTH, ScrollDirection.SOUTH), ANY(ScrollDirection.EAST, ScrollDirection.WEST,
							ScrollDirection.NORTH, ScrollDirection.SOUTH), NONE(ScrollDirection.NONE);

	private ScrollDirection[] directions;

	ScrollType(final ScrollDirection... directions) {
		this.directions = directions;
	}

	public ScrollDirection[] getDirections() {
		return directions;
	}

	public boolean isValidDirection(final ScrollDirection direction) {
		for (final ScrollDirection d : directions) {
			if (d == direction) {
				return true;
			}
		}
		return false;
	}

}
