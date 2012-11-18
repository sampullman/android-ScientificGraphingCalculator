// import com.android.calcApp.*;
// import cliCalc.*;

// import com.google.caliper.SimpleBenchmark;

// public class MyBenchmark extends SimpleBenchmark {
//     Calculater calc = new Calculator ();
//     float[] pts;
//     float unitLen;

//     @Override protected void setUp() {
//         pts = new float[320];
//         unitLen = (float)2/(float)40;
//     }

//     public void timeMyOperation(int reps) {
//       for (int i = 0; i < reps; i++) {
//           calc.graphFn (pts,-8,8,-10,-10,0,320,0,428,unitLen);
//       }
//     }
//   }