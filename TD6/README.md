# Fenwick Tree 🌲

A **Fenwick tree** is an efficient data structure that represents an array of integers with indices in `[L, H[` and allows performing the following operations in **O(log(H-L))** time:

## Available Operations ⚙️

### 1. `void inc(int i)`
- **Description**: Adds `1` to the element at index `i` in the array represented by `this`.
- **Condition**: Does nothing if `i` is not in `[L, H[`.

### 2. `int cumulative(int i)`
- **Description**: Returns the sum of all elements in the array represented by `this` with indices in `] -∞, i[ ∩ [L, H[`.


### 3. Application
- # Counting Inversions with Fenwick Trees 🌲

We will now use **Fenwick trees** to efficiently count inversions in an array.  

Given an array `tab` with indices in `[0, n[`, a pair of indices `{i, j}` is considered an **inversion** if the following conditions are met:  
1. `i < j`  
2. `tab[i] > tab[j]`
