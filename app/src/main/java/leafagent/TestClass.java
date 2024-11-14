package leafagent;

import leafagent.info.ActivityRoot;
import leafagent.info.LeafContainer;
import leafagent.info.TrunkContainer;

public class TestClass {
    private TrunkContainer branchContainer;

    public TestClass() {
        branchContainer = ActivityRoot.createChild("com.market.leafandroid.activities.sellers.SellersActivity");
        LeafContainer leafContainer = new LeafContainer(branchContainer, "addSellers");
        leafContainer.startTime(); //  <------
        leafContainer.endTime(); //  <------
    }
}

// // class version 65.0 (65)
//// access flags 0x21
//public class leafagent/TestClass {
//
//  // compiled from: TestClass.java
//
//  // access flags 0x2
//  private Lleafagent/info/TrunkContainer; branchContainer
//
//  // access flags 0x1
//  public <init>()V
//   L0
//    LINENUMBER 10 L0
//    ALOAD 0
//    INVOKESPECIAL java/lang/Object.<init> ()V
//   L1
//    LINENUMBER 11 L1
//    ALOAD 0
//    LDC "com.market.leafandroid.activities.sellers.SellersActivity"
//    INVOKESTATIC leafagent/info/ActivityRoot.createChild (Ljava/lang/String;)Lleafagent/info/TrunkContainer;
//    PUTFIELD leafagent/TestClass.branchContainer : Lleafagent/info/TrunkContainer;
//   L2
//    LINENUMBER 12 L2
//    NEW leafagent/info/LeafContainer
//    DUP
//    ALOAD 0
//    GETFIELD leafagent/TestClass.branchContainer : Lleafagent/info/TrunkContainer;
//    LDC "addSellers"
//    INVOKESPECIAL leafagent/info/LeafContainer.<init> (Lleafagent/info/BranchContainer;Ljava/lang/String;)V
//    ASTORE 1
//   L3
//    LINENUMBER 13 L3
//    ALOAD 1
//    INVOKEVIRTUAL leafagent/info/LeafContainer.startTime ()V
//   L4
//    LINENUMBER 14 L4
//    ALOAD 1
//    INVOKEVIRTUAL leafagent/info/LeafContainer.endTime ()V
//   L5
//    LINENUMBER 15 L5
//    RETURN
//   L6
//    LOCALVARIABLE this Lleafagent/TestClass; L0 L6 0
//    LOCALVARIABLE leafContainer Lleafagent/info/LeafContainer; L3 L6 1
//    MAXSTACK = 4
//    MAXLOCALS = 2
//}