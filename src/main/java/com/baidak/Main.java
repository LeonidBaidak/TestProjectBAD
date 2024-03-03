package com.baidak;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        long programStartTime = System.currentTimeMillis();

        if (args.length == 0) {
            throw new RuntimeException("No command line arguments was specified!");
        }
        String fileUri = args[0];

        System.out.println("The specified path to file is: " + fileUri);
        if (Objects.isNull(fileUri) || fileUri.isBlank() || Files.notExists(Paths.get(fileUri))) {
            throw new RuntimeException("Please, specify the valid path to data file!");
        }

        List<Integer> nums = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileUri))) {
            nums = new ArrayList<>();
            while (scanner.hasNextInt()) {
                nums.add(scanner.nextInt());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        long algorithmStartTime = System.currentTimeMillis();

        int maxSequenceLength = 0;
        int maxSequenceAnchor = 0;

        int minSequenceLength = 0;
        int minSequenceAnchor = 0;

        int maxNumber = 0;
        int minNumber = 0;
        int sum = 0;

        for (int i = 0; i < nums.size(); i++) {
            if (i > 0 && nums.get(i - 1) >= nums.get(i)) {
                maxSequenceAnchor = i;
            }

            if (i > 0 && nums.get(i - 1) <= nums.get(i)) {
                minSequenceAnchor = i;
            }

            maxSequenceLength = Math.max(maxSequenceLength, i - maxSequenceAnchor + 1);
            minSequenceLength = Math.max(minSequenceLength, i - minSequenceAnchor + 1);
            maxNumber = Math.max(maxNumber, nums.get(i));
            minNumber = Math.min(minNumber, nums.get(i));
            sum += nums.get(i);
        }
        float mean = (float) sum / ((float) nums.size());

        System.out.println("Length of the longest increasing sequence: " + maxSequenceLength);
        System.out.println("Length of the longest decreasing sequence: " + minSequenceLength);
        System.out.println("Max number: " + maxNumber);
        System.out.println("Min number: " + minNumber);
        System.out.println("Arithmetic mean: " + mean);
        System.out.println("Median: " + calculateMedian(nums));

        long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time in milli seconds: " + (endTime - programStartTime));
        System.out.println("The time spent by the algorithm on execution in milli seconds(actual working time): "
                + (endTime - algorithmStartTime));
    }

    private static float calculateMedian(List<Integer> nums) {
        //In order to find median, the data set must be sorted
        List<Integer> sortedNums = nums.stream()
                .sorted()
                .toList();
        int listSize = sortedNums.size();
        float median;
        if (listSize % 2 == 0)
            median = ((float) sortedNums.get(listSize / 2) + (float) sortedNums.get(listSize / 2 - 1)) / 2;
        else
            median = sortedNums.get(listSize / 2);

        return median;
    }
}