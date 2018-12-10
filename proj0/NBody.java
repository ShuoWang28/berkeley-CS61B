public class NBody{
  /** Given a file name, it should return a double corresponding to
  the radius of the universe in that file, e.g. readRadius("./data/planets.txt")
   should return 2.50e+11. */
   public static double readRadius(String path){
     In in = new In(path);
     int N = in.readInt();
     double radius = in.readDouble();
     return radius;
   }

  /** Given a file name, it should return an array of Planets corresponding to
   the planets in the file, e.g. readPlanets("./data/planets.txt")
   should return an array of five planets. */
   public static Planet[] readPlanets(String path){
     In in = new In(path);
     int N = in.readInt();
     Planet[] pArr = new Planet[N];
     double radius = in.readDouble();
     int i = 0;
     while(i < N){
       double xp = in.readDouble();
       double yp = in.readDouble();
       double xv = in.readDouble();
       double yv = in.readDouble();
       double m = in.readDouble();
       String img = in.readString();
       pArr[i] = new Planet(xp, yp, xv, yv, m, img);
       i++;
   }
   return pArr;
 }

 /** draw the universe in its starting position */
 public static void main(String[] args){
   /** Collecting All Needed Input */
   double T = Double.parseDouble(args[0]);
   double dt = Double.parseDouble(args[1]);
   String filename = args[2];
   double radius = NBody.readRadius(filename);
   Planet[] pArr = NBody.readPlanets(filename);
   int N = pArr.length;

  /** Drawing the Background */
  StdDraw.setScale(-radius, radius);
  String bg = "images/starfield.jpg";
  StdDraw.picture(0, 0, bg, 2 * radius, 2 * radius);

  /** draw each one of the planets in the planets array  */
  for (int i = 0; i < N; i++) {
    pArr[i].draw();
  }
 /** Enables double buffering.
   * An animation technique where all drawing takes place on the offscreen canvas.
   * Only when you call show() does your drawing get copied from the
   * offscreen canvas to the onscreen canvas, where it is displayed
   * in the standard drawing window. You don't have to understand this
   * for CS61B. Just know that if you don't call this function, any attempt
   * at smooth animation will look bad and flickery (remove it and see
   * what happens!). */
   StdDraw.enableDoubleBuffering();
   double t = 0;
   while(t < T){
     double[] xForces = new double[N];
     double[] yForces = new double[N];
     for(int i = 0; i < N; i++){
       xForces[i] = pArr[i].calcNetForceExertedByX(pArr);
       yForces[i] = pArr[i].calcNetForceExertedByY(pArr);
     }
     for(int i = 0; i < N; i++){
       pArr[i].update(dt, xForces[i], yForces[i]);
     }
     StdDraw.picture(0, 0, bg, 2 * radius, 2 * radius);
     /** draw each one of the planets in the planets array  */
     for (int i = 0; i < N; i++) {
       pArr[i].draw();
     }
     StdDraw.show();
     StdDraw.pause(10);
     t = t + dt;
   }
    StdOut.printf("%d\n", pArr.length);
    StdOut.printf("%.2e\n", radius);
     for (int i = 0; i < pArr.length; i++) {
       StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",pArr[i].xxPos, pArr[i].yyPos, pArr[i].xxVel,
               pArr[i].yyVel, pArr[i].mass, pArr[i].imgFileName);
      }
 }

}
