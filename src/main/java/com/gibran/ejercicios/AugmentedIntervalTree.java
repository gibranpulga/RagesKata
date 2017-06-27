package com.gibran.ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class AugmentedIntervalTree {

    public Interval root;

    public static Interval insertNode(Interval tmp, Interval newNode) {
        if (tmp == null) {
            tmp = newNode;
            tmp.setParent(null, null);
            return tmp;
        }

        if (newNode.getEnd() > tmp.getMax()) {
            tmp.setMax(newNode.getEnd());
        }

        if (tmp.compareTo(newNode) <= 0) {

            if (tmp.getRight() == null) {
            	newNode.setParent(tmp, false);
                tmp.setRight(newNode);
            }
            else {
                insertNode(tmp.getRight(), newNode);
            }
        } else {
            if (tmp.getLeft() == null) {
                tmp.setLeft(newNode);
                newNode.setParent(tmp, true);
            } else {
                insertNode(tmp.getLeft(), newNode);
            }
        }
        return tmp;
    }

    public static void printTree(Interval tmp) {
        if (tmp == null) {
            return;
        }

        if (tmp.getLeft() != null) {
            printTree(tmp.getLeft());
        }

        System.out.print(tmp);

        if (tmp.getRight() != null) {
            printTree(tmp.getRight());
        }
    }

    public void intersectInterval(Interval tmp, Interval i, List<Interval> res) {

        if (tmp == null) {
            return;
        }

        if (!((tmp.getStart() > i.getEnd()) || (tmp.getEnd() < i.getStart()))) {
            if (res == null) {
                res = new ArrayList<Interval>();
            }
            res.add(tmp);
        }

        if ((tmp.getLeft() != null) && (tmp.getLeft().getMax() >= i.getStart())) {
            this.intersectInterval(tmp.getLeft(), i, res);
        }

        this.intersectInterval(tmp.getRight(), i, res);
    }
    
    
    public void fillIntervalSubtree (Interval interval, List<Interval> intervals, boolean addInterval) {
        if (interval.getLeft() != null) {
        	fillIntervalSubtree(interval.getLeft(), intervals, true);
        }

        if (addInterval) {
        	intervals.add(interval);
        }

        if (interval.getRight() != null) {
        	fillIntervalSubtree(interval.getRight(), intervals, true);
        }
    }
    
    public void deleteIntervalSubtree (Interval interval) {
        if (interval.getLeft() != null) {
        	deleteIntervalSubtree(interval.getLeft());
        }

        Interval intervalRight = interval.getRight() != null ? interval.getRight() : null;
        System.out.println("Removing interval: " + interval.getStart() + " , " + interval.getEnd());
       	//interval = null;
       	interval.setStart(999);

        if (intervalRight != null) {
        	deleteIntervalSubtree(intervalRight);
        }
    }

    public static void main(String[] args) {

        AugmentedIntervalTree ait = new AugmentedIntervalTree();
        AugmentedIntervalTreeService aitService = new AugmentedIntervalTreeService();

        ait.root = insertNode(ait.root, new Interval(5, 10));
        ait.root = insertNode(ait.root, new Interval(15, 25));
        ait.root = insertNode(ait.root, new Interval(1, 12));
        ait.root = insertNode(ait.root, new Interval(8, 16));
        ait.root = insertNode(ait.root, new Interval(14, 20));
        ait.root = insertNode(ait.root, new Interval(18, 21));
        ait.root = insertNode(ait.root, new Interval(2, 8));

        printTree(ait.root);
        System.out.println("\n");

        /*
        List<Interval> queries = new ArrayList<>();
        queries.add(new Interval(8, 10));
        queries.add(new Interval(20, 22));
        */

        aitService.mergeIntervals(new Interval(8, 10), ait);
        System.out.println();
        printTree(ait.root);
    }
}

class AugmentedIntervalTreeService {
	
    public void mergeIntervals (Interval newInterval, AugmentedIntervalTree augmentedIntervalTree) {
    	AtomicLong lowestNumber = new AtomicLong();
    	AtomicLong highestNumber = new AtomicLong();
    	List<Interval> overlappedIntervals = new ArrayList<>();
    	augmentedIntervalTree.intersectInterval(augmentedIntervalTree.root, newInterval, overlappedIntervals);
    	if (overlappedIntervals.size() == 0) {
    		augmentedIntervalTree.root = AugmentedIntervalTree.insertNode(augmentedIntervalTree.root, newInterval);
    	} else {
    		List<Interval> intervalSubTree = new ArrayList<>();
    		System.out.println("Nodes overlapping [" + newInterval.getStart() + ", " + newInterval.getEnd() + "]:");
    		boolean initialIteration = true;
    		for (Interval interval : overlappedIntervals) {
    			System.out.println(interval.getStart() + ", " + interval.getEnd());
    			if (initialIteration) {
        			lowestNumber.getAndSet(interval.getStart() < newInterval.getStart() ? interval.getStart() : newInterval.getStart());
        			highestNumber.getAndSet(interval.getEnd() < newInterval.getEnd() ? interval.getEnd() : newInterval.getEnd());
        			initialIteration = false;
    			} else {
    				lowestNumber.getAndSet(interval.getStart() < lowestNumber.get() ? interval.getStart() : lowestNumber.get());
    				highestNumber.getAndSet(interval.getEnd() > highestNumber.get() ? interval.getEnd() : highestNumber.get());
    			}
    			System.out.println("New values: " + lowestNumber.get() + ", " + highestNumber.get());
    			augmentedIntervalTree.fillIntervalSubtree(interval, intervalSubTree, false);
    			// clear from memory
    			//deleteIntervalSubtree(interval);
    			interval.setLeft(null);
    			interval.setRight(null);
    			if (null == interval.isLeftNode()) {
    				augmentedIntervalTree = new AugmentedIntervalTree();
    			} else {
    				if (interval.isLeftNode()) {
    					interval.getParent().setLeft(null);
    				} else {
    					interval.getParent().setRight(null);
    				}
    			}
    			AugmentedIntervalTree.printTree(augmentedIntervalTree.root);
    			// clear left and right of all
    			for (Interval subtreeInterval: intervalSubTree) {
    				subtreeInterval.clear();
    				subtreeInterval.setLeft(null);
    				subtreeInterval.setRight(null);
    				subtreeInterval.setMax(subtreeInterval.getEnd());
    				augmentedIntervalTree.root = AugmentedIntervalTree.insertNode(augmentedIntervalTree.root, subtreeInterval);
    			}
    		}
    		AugmentedIntervalTree.printTree(augmentedIntervalTree.root);
    		augmentedIntervalTree.root = AugmentedIntervalTree.insertNode(augmentedIntervalTree.root, new Interval(lowestNumber.get(), highestNumber.get()));
    	}
    }

}