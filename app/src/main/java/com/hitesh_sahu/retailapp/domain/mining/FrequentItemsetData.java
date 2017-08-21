/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.domain.mining;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class holds the result information of a data-mining task.
 *
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Sep 14, 2015)
 */
public class FrequentItemsetData<I> {

    private final List<Set<I>> frequentItemsetList;
    private final Map<Set<I>, Integer> supportCountMap;
    private final double minimumSupport;
    private final int numberOfTransactions;

    FrequentItemsetData(List<Set<I>> frequentItemsetList,
                        Map<Set<I>, Integer> supportCountMap,
                        double minimumSupport,
                        int transactionNumber) {
        this.frequentItemsetList = frequentItemsetList;
        this.supportCountMap = supportCountMap;
        this.minimumSupport = minimumSupport;
        this.numberOfTransactions = transactionNumber;
    }

    public List<Set<I>> getFrequentItemsetList() {
        return frequentItemsetList;
    }

    public Map<Set<I>, Integer> getSupportCountMap() {
        return supportCountMap;
    }

    public double getMinimumSupport() {
        return minimumSupport;
    }

    public int getTransactionNumber() {
        return numberOfTransactions;
    }

    public double getSupport(Set<I> itemset) {
        return 1.0 * supportCountMap.get(itemset) / numberOfTransactions;
    }
}