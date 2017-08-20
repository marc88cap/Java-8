# Sorting, searching and Big O Notation
## What is Big O Notation?
Big O Notation describes how hard an algorithm has to work to solve a problem. Ratio between data to process and time.

## Sorting and searching Algorithms

[Bubble sort](BubbleSort.java) uses less memory than bucket sort but needs more time when looping through elements. O(n^2) - because it compares each element with each other element.
[Bucket sort](BucketSort.java) uses more memory than bubble sort because its storing values into 10 separate buckets (O(n)) which are the same length as original array, after sorting
all elements are stored back to the original array in a new order, this process repeats for as long as all values are sorted to the first (0) bucket. That makes this method O(n).
[Quick sort](QuickSort.java) sorts a list by finding its first elements right position (O(n)) and dividing (O(log n)) that list into two lists, one with smaller 
values than that elements value and the second with bigger values than the element. These lists are then recursively sorted again. 
This process repeats as long as there is only one element in the list. Which makes this method a "Order n log n" - O(n log n).

[Recursive Linear Search](RecursiveLinearSearch.java) can be a lot slower than [Recursive Binary Search](RecursiveBinarySearch.java) because its comparing each element with the searched key - O(n).
While the other is comparing a necessary sorted list if a value in the middle of a list is bigger, smaller or equal than the searched key - O(log n) because it compares only the middle value with 
the search key and divides the list in the middle if the comparison fails.
