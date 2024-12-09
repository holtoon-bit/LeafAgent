package leafagent;

import leafagent.info.ActivityRoot;
import leafagent.info.BranchContainer;
import leafagent.info.LeafContainer;
import leafagent.utils.JsonWriter;

public class TestClass extends TestClass1 {
    private BranchContainer branchContainer;

    @Override
    public void test() {
        super.test();
        JsonWriter.setProjectPath(getFilesDir().getPath());
        branchContainer = ActivityRoot.createChild("com.market.leafandroid.activities.sellers.SellersActivity");
        LeafContainer leafContainer = new LeafContainer(branchContainer, "addSellers");
        branchContainer.startTime();
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
//    LINENUMBER 8 L0
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
//    LINENUMBER 13 L0
//    ALOAD 0
//    INVOKESPECIAL leafagent/TestClass1.test ()V
//   L1
//    LINENUMBER 14 L1
//    ALOAD 0
//    INVOKEVIRTUAL leafagent/TestClass.getFilesDir ()Lleafagent/TestClass1;
//    INVOKEVIRTUAL leafagent/TestClass1.getPath ()Ljava/lang/String;
//    INVOKESTATIC leafagent/utils/JsonWriter.setProjectPath (Ljava/lang/String;)V
//   L2
//    LINENUMBER 15 L2
//    ALOAD 0
//    LDC "com.market.leafandroid.activities.sellers.SellersActivity"
//    INVOKESTATIC leafagent/info/ActivityRoot.createChild (Ljava/lang/String;)Lleafagent/info/TrunkContainer;
//    PUTFIELD leafagent/TestClass.branchContainer : Lleafagent/info/TrunkContainer;
//   L3
//    LINENUMBER 16 L3
//    NEW leafagent/info/LeafContainer
//    DUP
//    ALOAD 0
//    GETFIELD leafagent/TestClass.branchContainer : Lleafagent/info/TrunkContainer;
//    LDC "addSellers"
//    INVOKESPECIAL leafagent/info/LeafContainer.<init> (Lleafagent/info/BranchContainer;Ljava/lang/String;)V
//    ASTORE 1
//   L4
//    LINENUMBER 17 L4
//    ALOAD 1
//    INVOKEVIRTUAL leafagent/info/LeafContainer.startTime ()V
//   L5
//    LINENUMBER 18 L5
//    ALOAD 1
//    INVOKEVIRTUAL leafagent/info/LeafContainer.endTime ()V
//   L6
//    LINENUMBER 19 L6
//    RETURN
//   L7
//    LOCALVARIABLE this Lleafagent/TestClass; L0 L7 0
//    LOCALVARIABLE leafContainer Lleafagent/info/LeafContainer; L4 L7 1
//    MAXSTACK = 4
//    MAXLOCALS = 2
//}