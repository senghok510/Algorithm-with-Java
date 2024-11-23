public class Fenwick {
    final Fenwick left;
    final Fenwick right;
    final int lo;
    final int hi;
    int acc;




    Fenwick(int lo,int hi){

        this.lo = lo;
        this.hi = hi;
        this.acc = 0;


        if(hi-lo>1){
            left = new Fenwick(lo, (lo+hi)/2);
            right = new Fenwick((lo+hi)/2,hi);
        }
        else{
            left = null;
            right = null;
        }

    }

//we try to get the node that contaiin [i,i+1[
    Fenwick get(int i){
        if(i<lo || i>=hi){
            return null;
        }
        Fenwick f = this;   //variable de récursive

        while(f.left != null || f.right != null){
            if(f.left.hi >= i+1){
                f = f.left;
            }
            else{
                f = f.right;
            }


        }
        return f;
    }

//this method want to add 1 to the nodes of the tree which has intervall that conclude i
    void inc(int i){
        if(i>hi || i < lo){

            return;
        }

        
        if(i < hi && i >= lo){
            this.acc++;
            
        }
        if( this.right != null && this.left !=null){
            this.right.inc(i);
            this.left.inc(i);
        }
    }


    //give sum of element del indice toch daj jeang i rbos tableau del domnang oy tree.
    int cumulative(int i){
        int result = 0;

        if(i>= hi){
            return this.acc;
        }

        if(i<lo){
            return 0;
        }


        if(lo<i && i <hi){
            if(this.left !=null && this.right !=null){
                result = left.cumulative(i) + right.cumulative(i);
        }
            else{
                return 0;
            }




        }   
   
        return result;

  
    }


    //use Fenwick for dak lek rbos inversion knong table muy

    //we will count the number of inversion


    




}