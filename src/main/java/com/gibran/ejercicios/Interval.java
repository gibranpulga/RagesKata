package com.gibran.ejercicios;

class Interval implements Comparable<Interval> {

    public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public Interval getLeft() {
		return left;
	}

	public void setLeft(Interval left) {
		this.left = left;
	}

	public Interval getRight() {
		return right;
	}

	public void setRight(Interval right) {
		this.right = right;
	}

	private long     start;
    private long     end;
    private long     max;
    private Interval left;
    private Interval right;
    private Interval parent;
    private Boolean leftNode;

    public Boolean isLeftNode() {
		return leftNode;
	}

	public Interval(long start, long end) {

        this.start = start;
        this.end = end;
        this.max = end;
    }
 
    @Override
    public String toString() {
        return "[" + this.getStart() + ", " + this.getEnd() + ", "
               + this.getMax() + "]";
    }

    @Override
    public int compareTo(Interval i) {
        if (this.start < i.start) {
            return -1;
        }
        else if (this.start == i.start) {
            return this.end <= i.end ? -1 : 1;
        }
        else {
            return 1;
        }
    }

    public Interval getParent () {
    	return this.parent;
    }

    public void setParent (Interval parent, Boolean leftNode) {
    	this.parent = parent;
    	this.leftNode = leftNode;
    }
    
    public void clear () {
    	//start = null;
        //end = null;
        //max = null;
        left = null;
        right = null;
        parent = null;
        leftNode = null;
    }
    
}