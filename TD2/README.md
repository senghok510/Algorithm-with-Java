# Fruit Elimination Game 🍎🍊

We study a grid-based game where fruits are placed and moved on a rectangular grid. The goal is to analyze **stable configurations** of fruits, where no eliminations occur. Here's how the game works:

---

## Game Rules 🛠️

1. **Grid Setup**:
   - The game is played on a rectangular grid, where each square can contain only one fruit.
   - There are multiple types of fruit.

2. **Elimination**:
   - If **three or more fruits of the same type** are adjacent in a row or column, they are **eliminated** and disappear from the grid.
   - The player scores points based on the number of fruits eliminated.

3. **Stable Configurations**:
   - A configuration is considered **stable** if no three adjacent fruits of the same type appear in any row or column.
   - In stable configurations, no eliminations occur.

---

## Objective 🎯

The goal of this tutorial is to:
1. **Count the Number of Stable Configurations**:
   - Given a grid of size **10x10**.
   - With **two types of fruit** available.
   
2. **Explore Algorithmic Approaches**:
   - Efficiently calculate the number of valid stable configurations using constraints.

---

## Challenges 🚧

- **Constraints**:
  - The grid size (10x10) and the limited number of fruit types create complex constraints to ensure stability.
- **Combinatorics**:
  - Counting valid configurations involves generating and testing arrangements while avoiding three adjacent fruits of the same type.

---

This tutorial provides an opportunity to delve into combinatorial problem-solving, grid-based algorithms, and optimization techniques to solve a fun and engaging problem.
