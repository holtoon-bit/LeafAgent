package leafagent;

import leafagent.info.ActivityRoot;
import leafagent.info.LeafContainer;
import leafagent.info.TrunkContainer;

public class TestClass extends TestClass1 {
    private TrunkContainer branchContainer;

    @Override
    public void test() {
        super.test();
        branchContainer = ActivityRoot.createChild("com.market.leafandroid.activities.sellers.SellersActivity");
        LeafContainer leafContainer = new LeafContainer("addSellers");
        leafContainer.startTime(); //  <------
        leafContainer.endTime(); //  <------
    }
}

//// class version 65.0 (65)
//// access flags 0x21
//public class leafagent/TestClass extends leafagent/TestClass1 {
//
//  // compiled from: TestClass.java
//
//  // access flags 0x2
//  private Lleafagent/info/TrunkContainer; branchContainer
//
//  // access flags 0x1
//  public <init>()V
//   L0
//    LINENUMBER 7 L0
//    ALOAD 0
//    INVOKESPECIAL leafagent/TestClass1.<init> ()V
//    RETURN
//   L1
//    LOCALVARIABLE this Lleafagent/TestClass; L0 L1 0
//    MAXSTACK = 1
//    MAXLOCALS = 1
//
//  // access flags 0x1
//  public test()V
//   L0
//    LINENUMBER 12 L0
//    ALOAD 0
//    INVOKESPECIAL leafagent/TestClass1.test ()V
//   L1
//    LINENUMBER 13 L1
//    ALOAD 0
//    LDC "com.market.leafandroid.activities.sellers.SellersActivity"
//    INVOKESTATIC leafagent/info/ActivityRoot.createChild (Ljava/lang/String;)Lleafagent/info/TrunkContainer;
//    PUTFIELD leafagent/TestClass.branchContainer : Lleafagent/info/TrunkContainer;
//   L2
//    LINENUMBER 14 L2
//    NEW leafagent/info/LeafContainer
//    DUP
//    LDC "addSellers"
//    INVOKESPECIAL leafagent/info/LeafContainer.<init> (Ljava/lang/String;)V
//    ASTORE 1
//   L3
//    LINENUMBER 15 L3
//    ALOAD 1
//    INVOKEVIRTUAL leafagent/info/LeafContainer.startTime ()V
//   L4
//    LINENUMBER 16 L4
//    ALOAD 1
//    INVOKEVIRTUAL leafagent/info/LeafContainer.endTime ()V
//   L5
//    LINENUMBER 17 L5
//    RETURN
//   L6
//    LOCALVARIABLE this Lleafagent/TestClass; L0 L6 0
//    LOCALVARIABLE leafContainer Lleafagent/info/LeafContainer; L3 L6 1
//    MAXSTACK = 3
//    MAXLOCALS = 2
//}