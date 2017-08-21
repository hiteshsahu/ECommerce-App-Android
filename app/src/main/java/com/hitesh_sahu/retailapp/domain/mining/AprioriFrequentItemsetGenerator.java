/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.domain.mining;

import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class implements the
 * <a href="https://en.wikipedia.org/wiki/Apriori_algorithm">Apriori algorithm</a>
 * for frequent itemset generation.
 *
 * @param <I> the actual item type.
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Sep 14, 2015)
 */
public class AprioriFrequentItemsetGenerator<I> {

    private static final Comparator ITEM_COMPARATOR = new Comparator() {

        @Override
        public int compare(Object o1, Object o2) {
            return ((Comparable) o1).compareTo(o2);
        }

    };

    /**
     * Generates the frequent itemset data.
     *
     * @param transactionList the list of transactions to mine.
     * @param minimumSupport  the minimum support.
     * @return the object describing the result of this task.
     */
    public FrequentItemsetData<I> generate(List<Set<I>> transactionList,
                                           double minimumSupport) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(transactionList, "The itemset list is empty.");
        } else {
            if (transactionList == null)
                throw new NullPointerException("The itemset list is empty.");
        }


        checkSupport(minimumSupport);

        if (transactionList.isEmpty()) {
            return null;
        }

        // Maps each itemset to its support count. Support count is simply the
        // number of times an itemset appeares in the transaction list.
        Map<Set<I>, Integer> supportCountMap = new HashMap<>();

        // Get the list of 1-itemsets that are frequent.
        List<Set<I>> frequentItemList = findFrequentItems(transactionList,
                supportCountMap,
                minimumSupport);

        // Maps each 'k' to the list of frequent k-itemsets.
        Map<Integer, List<Set<I>>> map = new HashMap<>();
        map.put(1, frequentItemList);

        // 'k' denotes the cardinality of itemsets processed at each iteration
        // of the following loop.
        int k = 1;

        do {
            ++k;

            // First generate the candidates.
            List<Set<I>> candidateList =
                    generateCandidates(map.get(k - 1));

            for (Set<I> transaction : transactionList) {
                List<Set<I>> candidateList2 = subset(candidateList,
                        transaction);

                for (Set<I> itemset : candidateList2) {
//                    supportCountMap.put(itemset,
//                            supportCountMap.getOrDefault(itemset,
//                                    0) + 1);

                    Integer value = supportCountMap.get(itemset);
                    if (value == null) {
                        value = 0;
                    }
                    supportCountMap.put(itemset, value + 1);
                }
            }

            map.put(k, getNextItemsets(candidateList,
                    supportCountMap,
                    minimumSupport,
                    transactionList.size()));

        } while (!map.get(k).isEmpty());

        return new FrequentItemsetData<>(extractFrequentItemsets(map),
                supportCountMap,
                minimumSupport,
                transactionList.size());
    }

    /**
     * This method simply concatenates all the lists of frequent itemsets into
     * one list.
     *
     * @param map the map mapping an itemset size to the list of frequent
     *            itemsets of that size.
     * @return the list of all frequent itemsets.
     */
    private List<Set<I>>
    extractFrequentItemsets(Map<Integer, List<Set<I>>> map) {
        List<Set<I>> ret = new ArrayList<>();

        for (List<Set<I>> itemsetList : map.values()) {
            ret.addAll(itemsetList);
        }

        return ret;
    }

    /**
     * This method gathers all the frequent candidate itemsets into a single
     * list.
     *
     * @param candidateList   the list of candidate itemsets.
     * @param supportCountMap the map mapping each itemset to its support count.
     * @param minimumSupport  the minimum support.
     * @param transactions    the total number of transactions.
     * @return a list of frequent itemset candidates.
     */
    private List<Set<I>> getNextItemsets(List<Set<I>> candidateList,
                                         Map<Set<I>, Integer> supportCountMap,
                                         double minimumSupport,
                                         int transactions) {
        List<Set<I>> ret = new ArrayList<>(candidateList.size());

        for (Set<I> itemset : candidateList) {
            if (supportCountMap.containsKey(itemset)) {
                int supportCount = supportCountMap.get(itemset);
                double support = 1.0 * supportCount / transactions;

                if (support >= minimumSupport) {
                    ret.add(itemset);
                }
            }
        }

        return ret;
    }

    /**
     * Computes the list of itemsets that are all subsets of
     * {@code transaction}.
     *
     * @param candidateList the list of candidate itemsets.
     * @param transaction   the transaction to test against.
     * @return the list of itemsets that are subsets of {@code transaction}
     * itemset.
     */
    private List<Set<I>> subset(List<Set<I>> candidateList,
                                Set<I> transaction) {
        List<Set<I>> ret = new ArrayList<>(candidateList.size());

        for (Set<I> candidate : candidateList) {
            if (transaction.containsAll(candidate)) {
                ret.add(candidate);
            }
        }

        return ret;
    }

    /**
     * Generates the next candidates. This is so called F_(k - 1) x F_(k - 1)
     * method.
     *
     * @param itemsetList the list of source itemsets, each of size <b>k</b>.
     * @return the list of candidates each of size <b>k + 1</b>.
     */
    private List<Set<I>> generateCandidates(List<Set<I>> itemsetList) {
        List<List<I>> list = new ArrayList<>(itemsetList.size());

        for (Set<I> itemset : itemsetList) {
            List<I> l = new ArrayList<>(itemset);
            Collections.<I>sort(l, ITEM_COMPARATOR);
            list.add(l);
        }

        int listSize = list.size();

        List<Set<I>> ret = new ArrayList<>(listSize);

        for (int i = 0; i < listSize; ++i) {
            for (int j = i + 1; j < listSize; ++j) {
                Set<I> candidate = tryMergeItemsets(list.get(i), list.get(j));

                if (candidate != null) {
                    ret.add(candidate);
                }
            }
        }

        return ret;
    }

    /**
     * Attempts the actual construction of the next itemset candidate.
     *
     * @param itemset1 the list of elements in the first itemset.
     * @param itemset2 the list of elements in the second itemset.
     * @return a merged itemset candidate or {@code null} if one cannot be
     * constructed from the input itemsets.
     */
    private Set<I> tryMergeItemsets(List<I> itemset1, List<I> itemset2) {
        int length = itemset1.size();

        for (int i = 0; i < length - 1; ++i) {
            if (!itemset1.get(i).equals(itemset2.get(i))) {
                return null;
            }
        }

        if (itemset1.get(length - 1).equals(itemset2.get(length - 1))) {
            return null;
        }

        Set<I> ret = new HashSet<>(length + 1);

        for (int i = 0; i < length - 1; ++i) {
            ret.add(itemset1.get(i));
        }

        ret.add(itemset1.get(length - 1));
        ret.add(itemset2.get(length - 1));
        return ret;
    }

    /**
     * Computes the frequent itemsets of size 1.
     *
     * @param itemsetList     the entire database of transactions.
     * @param supportCountMap the support count map to which to write the
     *                        support counts of each item.
     * @param minimumSupport  the minimum support.
     * @return the list of frequent one-itemsets.
     */
    private List<Set<I>> findFrequentItems(List<Set<I>> itemsetList,
                                           Map<Set<I>, Integer> supportCountMap,
                                           double minimumSupport) {
        Map<I, Integer> map = new HashMap<>();

        // Count the support counts of each item.
        for (Set<I> itemset : itemsetList) {
            for (I item : itemset) {
                Set<I> tmp = new HashSet<>(1);
                tmp.add(item);

                if (supportCountMap.containsKey(tmp)) {
                    supportCountMap.put(tmp, supportCountMap.get(tmp) + 1);
                } else {
                    supportCountMap.put(tmp, 1);
                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // map.put(item, map.getOrDefault(item, 0) + 1);
//                } else {
//                    for (Set<I> itemsets : item) {
                Integer value = supportCountMap.get(item);
                if (value == null) {
                    value = 0;
                }
                map.put(item, value + 1);
//                    }
//                }
            }
        }

        List<Set<I>> frequentItemsetList = new ArrayList<>();

        for (Map.Entry<I, Integer> entry : map.entrySet()) {
            if (1.0 * entry.getValue() / map.size() >= minimumSupport) {
                Set<I> itemset = new HashSet<>(1);
                itemset.add(entry.getKey());
                frequentItemsetList.add(itemset);
            }
        }

        return frequentItemsetList;
    }

    private void checkSupport(double support) {
        if (Double.isNaN(support)) {
            throw new IllegalArgumentException("The input support is NaN.");
        }

        if (support > 1.0) {
            throw new IllegalArgumentException(
                    "The input support is too large: " + support + ", " +
                            "should be at most 1.0");
        }

        if (support < 0.0) {
            throw new IllegalArgumentException(
                    "The input support is too small: " + support + ", " +
                            "should be at least 0.0");
        }
    }
}