


public class CountInversions {

    static int countInversionsNaive(int[] a){
        int n =0;
        for(int i =0; i<a.length; i++){
            for(int j =0; j<a.length ; j++){
                if(i<j && a[i] > a[j]){
                    n++;
                }
            }
        }
        return n;



    }
    static int min(int[] a){
        int min=a[0];

        for(int i =0; i<a.length;i++){
           
            if(a[i] <min){
                min = a[i];
            }
        }
        return min;



    }
    static int max(int[] a){
        int max =a[0];

        for(int i =0; i<a.length; i++){
            if(a[i] > max){
                max = a[i];
            }



        }
        return max;

    }


//this time we try to count the number of inversion of a table using Fenwick tree
    static int countInversionsFen(int[] a){
        if(a.length ==0){
            return 0;
        }
        int nb_inversion=0;
        Fenwick fen = new Fenwick(min(a) , max(a)+1);
        

        for( int i =a.length-1; i>=0;i--){
          
            nb_inversion = nb_inversion + fen.cumulative(a[i]);
            fen.inc(a[i]);
            
        }
        return nb_inversion;



    }
    

}
