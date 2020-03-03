/**
 * cse250.pa1.DataEntryStore.scala
 *
 * Copyright 2020 Andrew Hughes (ahughes6@buffalo.edu)
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-sa/4.0/.
 *
 * Submission author
 * UBIT:garyfeng
 * Person#:50242102
 *
 * Collaborators (include UBIT name of each, comma separated):
 * UBIT:
 */
package cse250.pa1

import cse250.objects.{EmbeddedNode, EmbeddedEmpty, EmbeddedListNode}

class
DataEntryStore[A](private val _capacity: Int = 100)
  extends collection.mutable.Seq[A] {
  // These private members should not be modified.
  private val _emptyNode = new EmbeddedEmpty[A]
  private val _dataArray = Array.fill[EmbeddedListNode[A]](_capacity)(_emptyNode)
  private var _headIndex = -1
  private var _tailIndex = -1
  private var _numStored = 0

  // Public getters for private members.
  def dataArray = _dataArray
  def headIndex = _headIndex
  def tailIndex = _tailIndex
  def emptyNode = _emptyNode

  /** Inserts element to tail of list. */
  def insert(elem: A): Unit = {
    var previousIndex = 0
    if (_numStored == 0){
      _headIndex = 0
      _tailIndex = 0
      _dataArray(_tailIndex).value = elem
    } else if (_numStored == _capacity) {
      //when it hits capacity it'll wrap back around
      previousIndex = _tailIndex
      _tailIndex = _headIndex
      if (_headIndex == _capacity - 1){
        _headIndex = 0
      } else {
        _headIndex += 1
      }
      _dataArray(_tailIndex).value = elem
      _dataArray(_tailIndex).prev = previousIndex
      _dataArray(_tailIndex).next = -1
      _dataArray(previousIndex).next = _tailIndex
      _dataArray(_headIndex).prev = -1
      _numStored -= 1
    } else {
      previousIndex = _tailIndex
      _tailIndex += 1
      _dataArray(_tailIndex).value = elem
      _dataArray(_tailIndex).prev = previousIndex
      _dataArray(previousIndex).next = _tailIndex
    }
    _numStored += 1
  }

  /** Removes all copies of the given element. */
  def remove(elem: A): Boolean = {
    ???
  }

  /** Returns the count of nodes containing given entry. */
  def countEntry(entry: A): Int = {
    var sum = 0
    for (entries <- _dataArray.iterator){
      if (entries.value == entry){
        sum += 1
      }
    }
    sum
  }

  /** Gets the element at the specified index. */
  override def apply(idx: Int): A = {
    require(idx >= 0 && idx < _numStored)
    _dataArray(idx).value.get
  }

  /** Replaces element at given index with a new value. */
  override def update(idx: Int, elem: A): Unit = {
    _dataArray(idx).value = elem
  }

  /** Returns an Iterator that can be used only once. */
  def iterator: Iterator[A] = new Iterator[A] {
    private var currentIndex = _headIndex

    override def hasNext: Boolean = currentIndex != -1

    override def next(): A = {
      val previousIndex = currentIndex
      currentIndex = _dataArray(currentIndex).next
      _dataArray(previousIndex).value.get
    }
  }

  /** Returns the length of the stored list. */
  override def length: Int = _numStored

  override def toString: String = if (_numStored == 0) "" else this.iterator.addString(new StringBuilder, "DataEntryStore: (", ",", ")\n").result()
}
