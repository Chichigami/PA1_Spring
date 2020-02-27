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
 * UBIT:
 * Person#:
 *
 * Collaborators (include UBIT name of each, comma separated):
 * UBIT:
 */
package cse250.pa1

import cse250.objects.{EmbeddedNode, EmbeddedEmpty, EmbeddedListNode}

class DataEntryStore[A](private val _capacity: Int = 100)
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
  def insert(elem: A): Unit = ???

  /** Removes all copies of the given element. */
  def remove(elem: A): Boolean = ???

  /** Returns the count of nodes containing given entry. */
  def countEntry(entry: A): Int = ???

  /** Gets the element at the specified index. */
  override def apply(idx: Int): A = ???

  /** Replaces element at given index with a new value. */
  override def update(idx: Int, elem: A): Unit = ???

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
