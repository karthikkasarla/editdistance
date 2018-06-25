import java.util.*;
public class editdist {
	private static final int DelCost=3;
	private static final int InsCost=3;
	private static final int Sentinel=Integer.MAX_VALUE;
	private static final int CopCost=4;
	private static final int RepCost=2;
	private static final int TwiddleCost=0;
	private static final int KillCost=2;
	public static void main(String[] args) {
		Scanner scann=new Scanner(System.in);
		System.out.println("Enter the string to transform");
		String input=scann.nextLine();
		char[] in=input.toCharArray();
		/*for(int i=0;i<in.length;i++){
			System.out.println(in[i]);
		}*/
		System.out.println("enter the string transformed");
		String output=scann.nextLine();
		char[] out=output.toCharArray();
		int inlen=in.length;
		int outlen=out.length;
		System.out.println(inlen);
		System.out.println(outlen);
		int [][] T = new int[inlen][outlen];
		String[][] Opseq=new String[inlen][outlen];
		int c=EditDistance(in,out,T,Opseq,inlen,outlen);
		System.out.println("Transformed matrixes are");
		for(int i=0;i<inlen;i++){
			for(int j=0;j<outlen;j++){
				System.out.print(T[i][j]+"  ");
			}
			System.out.println();
		}
		
	/*	for(int i=0;i<inlen;i++){
			for(int j=0;j<outlen;j++){
				System.out.println(Opseq[i][j]);
			}
		}
		*/
		System.out.println("Minimal cost to perform transformations are:"+T[inlen-1][outlen-1]);
		System.out.println("Optimal sequence of execution are ");
		opsequence(Opseq,inlen-1,outlen-1,c);
		
		}
	
public static int EditDistance(char[] in,char[] out,int[][] T,String[][] Op,int il,int ol){
	int a=0,c,d,e,f,g;
	c=Integer.MAX_VALUE;;
	d=Integer.MAX_VALUE;
	e=Integer.MAX_VALUE;
	f=Integer.MAX_VALUE;
	g=Integer.MAX_VALUE;
	for(int i=0;i<il;i++){
		for(int j=0;j<ol;j++){
			T[i][j]=Sentinel;
		}
	}
	for(int i=0;i<il;i++){
		T[i][0]=i*DelCost;
		Op[i][0]="Delete";
	}
	for(int j=0;j<ol;j++){
		T[0][j]=j*InsCost;
		Op[0][j]="Insert";
	}
	for(int i=1;i<il;i++){
		for(int j=1;j<ol;j++){
		
			if(in[i]==out[j])
				c=T[i-1][j-1]+CopCost;
			if(in[i]!=out[j] && (T[i-1][j-1]+RepCost<T[i][j]))
				d=T[i-1][j-1]+RepCost;
			if(i>=2 && j>=2 &&(in[i]==out[j-1]) && (in[i-1]==out[j]) && ((T[i-2][j-2]+TwiddleCost )< T[i][j]) )
				e=T[i-2][j-2]+TwiddleCost;
			if(T[i-1][j]+DelCost< T[i][j])
				f=T[i-1][j]+DelCost;
			if(T[i][j-1]+InsCost<T[i][j])
				g=T[i][j-1]+InsCost;        
			T[i][j]=min(min(min(min(c,d),e),f),g);
			
			//System.out.println(T[i][j]);
			if(T[i][j]==c){
				Op[i][j]="Copy";
				
			}
			if(T[i][j]==d){
				Op[i][j]="Replace";
				
			}if(T[i][j]==e){
				Op[i][j]="Twiddle";
				
			}if(T[i][j]==f){
				Op[i][j]="Delete";
				
			}if(T[i][j]==g){
				Op[i][j]="Insert";
				
			}
			c=Integer.MAX_VALUE;
			d=Integer.MAX_VALUE;
			e=Integer.MAX_VALUE;
			f=Integer.MAX_VALUE;
			g=Integer.MAX_VALUE;
		}
	}
	for(int i=0;i<il;i++){
		if(T[i][ol-1]+KillCost<T[il-1][ol-1]){
			T[il-1][ol-1]=T[i][ol-1]+KillCost;
			Op[il-1][ol-1]="Kill";
			a=i;
		}
	}
	return a;
}
public static int min(int a,int b){
	return a<b?a:b;
		
}
public static void opsequence(String[][] op,int i,int j,int Killval){
	int inew,jnew;
	if(i==0 && j==0){
		return;
	}
	if(op[i][j]=="Copy" || op[i][j]=="Replace"){
		inew=i-1;
		jnew=j-1;
	}
	else if (op[i][j]=="Twiddle"){
		inew=i-2;
		jnew=j-2;
	}
	else if(op[i][j]=="Delete"){
		inew=i-1;
		jnew=j;
	}
	else if(op[i][j]=="Insert"){
		inew=i;
		jnew=j-1;
	}
	else{
		inew=Killval;
		jnew=j;
	}
	System.out.println(op[i][j]);
	opsequence(op,inew,jnew,Killval);	
}
}