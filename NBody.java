/* Name: Ethan Chen
 * PennKey: etc
 * Recitation: 217
 * 
 * Execution: java NBody
 * 
 * Generates a simulation of the solar system using Newton's universal law
 * of graviation
 */

public class NBody {
    public static void main(String[] args) {
        
        //Time simulation should end
        double simulationTime = Double.parseDouble(args[0]);
        
        //time quantum
        double timeStep = Double.parseDouble(args[1]);
        
        //filename of universe information
        String filename = args[2];
        
        //creares a variable inStream to read from the file
        In inStream = new In(filename);
        
        //number of particles in program
        int numParticles = inStream.readInt();
        
        //radius of universe
        double radius = inStream.readDouble();
        
        //variables of the particles
        double[] m = new double [numParticles];
        double[] px = new double [numParticles];
        double[] py = new double [numParticles];
        double[] vx = new double [numParticles];
        double[] vy = new double [numParticles];
        String[] img = new String [numParticles];
        
        //Values in numParticles
        for (int i = 0; i < numParticles; i++) {
            m[i] = inStream.readDouble();
            px[i] = inStream.readDouble();
            py[i] = inStream.readDouble();
            vx[i] = inStream.readDouble();
            vy[i] = inStream.readDouble();
            img[i] = inStream.readString();
        }  
        
        //set coordinates of simulation window
        PennDraw.setXscale(-radius, radius);
        PennDraw.setYscale(-radius, radius);
        
        //time loop to draw system
        PennDraw.enableAnimation(30);
        for (int i = (int) timeStep / 2; i < simulationTime; i += timeStep) {
            
            //draw starfield in center
            PennDraw.picture(0, 0, "starfield.jpg");
            
            double[] accelerationX = new double[numParticles];
            double[] accelerationY = new double[numParticles];
            
            // Calculate all forces
            for (int j = 0; j < numParticles; j++) {
                PennDraw.picture(px[j], py[j], img[j]);
                for (int k = j + 1; k < numParticles; k++) {
                    double G = 6.67e-11;
                    double dx = px[k] - px[j];
                    double dy = py[k] - py[j];
                    double d = Math.sqrt(dx * dx + dy * dy);
                    // Attraction force on body j
                    accelerationX[j] += G * m[k] * (dx / d / d / d);
                    accelerationY[j] += G * m[k] * (dy / d / d / d);
                    // Attraction force on body k
                    accelerationX[k] += G * m[j] * (-dx / d / d / d);
                    accelerationY[k] += G * m[j] * (-dy / d / d / d);
                }
                
                vx[j] += timeStep * accelerationX[j];
                vy[j] += timeStep * accelerationY[j];
                
                px[j] += timeStep * vx[j];
                py[j] += timeStep * vy[j];
            }
            
//            //Draw particles
//            for (int j = 0; j < numParticles; j++) {
//                PennDraw.picture(px[j], py[j], img[j]);
//                
//                double forceX = 0;
//                double forceY = 0;
//                
//                //compute forces exerted by other particles
//                for (int k = 0; k < numParticles; k++) {
//                    if (k != j) {
//                        double G = 6.67e-11;
//                        double dx = px[k] - px[j];
//                        double dy = py[k] - py[j];
//                        double d = Math.sqrt(dx * dx + dy * dy);
//                        double force = ((G * m[j]) / (d * d)) * m[k];
//                        forceX += force * (dx / d);
//                        forceY += force * (dy / d);
//                    }
//                }
//                
//                double accelerationX = forceX / m[j];
//                double accelerationY = forceY / m[j];
//                
//                vx[j] += timeStep * accelerationX;
//                vy[j] += timeStep * accelerationY;
//                
//                px[j] += timeStep * vx[j];
//                py[j] += timeStep * vy[j];
//            }
            PennDraw.advance();
            
        }
        System.out.printf("%d\n", numParticles);
        System.out.printf("%.2e\n", radius);
        for (int i = 0; i < numParticles; i++) {
            System.out.printf("%12.5e %12.5e %12.5e %12.5e %12.5e %12s\n",
                              m[i], px[i], py[i], vx[i], vy[i], img[i]);
        } 
        
    }
    
}