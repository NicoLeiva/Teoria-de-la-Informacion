package TeoriaTP;

import java.util.*;

public class ArbolHuffman {
	NodoArbolHuffman raiz;
	ArrayList<NodoArbolHuffman> listaNodos = new ArrayList<NodoArbolHuffman>();
	protected ArrayList <Tono> l = new ArrayList <Tono>();
	
	public ArbolHuffman(){
			}
	
	public ArbolHuffman(int[][] matrizImagen){
		obtenerFrecuencias(matrizImagen);
		this.generarNodosHuffman();
		this.generarCodificacion();
		this.obtenerCodificacion();
	}
	
	public ArbolHuffman(ArrayList<NodoArbolHuffman> lista){
		this.listaNodos = lista;
		this.generarCodificacion();
		this.obtenerCodificacion();
	}
	
	public NodoArbolHuffman getRaiz(){
		return this.raiz;
	}
	public ArrayList<Tono> getTonos(){
		//ArrayList<Tono> aux = new ArrayList<Tono>();
		return new ArrayList<Tono> (l);
	}

	public boolean existeTono(int c){
		for (Tono t : l) {
			if(t.getCodigo()==c)
				return true;
			
		}
	return false;
	}
	public void aumentarAparicionesTono(int c){
		for (Tono t : l) {
			if(t.getCodigo()==c)
				t.aumentarApariciones();
			
		}
	}
	public void addTono(String n,int c){
		Tono t = new Tono(n,c);
		l.add(t);
	}
	
	public int getTamano(){
		return l.size();
	}
	
	public Tono getTono(int i){
		return l.get(i);
			
		}
	public void obtenerFrecuencias(int[][] matriz){
		float cant= matriz.length*matriz[0].length;
		for (int fil = 0; fil < matriz.length; fil++) {
			for (int col = 0; col < matriz[0].length; col++){
				if(existeTono(matriz[fil][col])){
						aumentarAparicionesTono(matriz[fil][col]);}
				else{
					addTono(String.valueOf(matriz[fil][col]),matriz[fil][col]);
					}	
			}	
		}
		for (int k = 0; k < getTamano(); k++) {
			this.getTono(k).setFrecuencia(getTono(k).getApariciones()/cant);
			}
	}

	
	//Este metodo me va a devolver los dos nodos con menores frecuencias de la lista
	private ArrayList<NodoArbolHuffman> obtenerMenoresFrecuencias (ArrayList<NodoArbolHuffman> listaNodosAux){
		ArrayList<NodoArbolHuffman> result = new ArrayList<NodoArbolHuffman>();
		float frecMinMenor = 99;
		NodoArbolHuffman minMenor= new NodoArbolHuffman();
		float frecMinMayor = 100;
		NodoArbolHuffman minMayor= new NodoArbolHuffman();
		
		for(int i=0; i<listaNodosAux.size(); i++){
			if(listaNodosAux.get(i).getFrecuencia()<frecMinMayor){
				if(listaNodosAux.get(i).getFrecuencia()<frecMinMenor){
					minMayor = minMenor;
					frecMinMayor = frecMinMenor;
					minMenor = listaNodosAux.get(i);
					frecMinMenor = minMenor.getFrecuencia();
				}else{
					minMayor = listaNodosAux.get(i);
					frecMinMayor = minMayor.getFrecuencia();
				}
			}
		}
		result.add(minMayor);
		result.add(minMenor);

		return result;
		
	}
	public void generarNodosHuffman(){
		for (Tono t : l) {
			NodoArbolHuffman nodoD = new NodoArbolHuffman(t.getNombre(),t.getCodigo(), t.getFrecuencia());
			listaNodos.add(nodoD);
		}
		
	}
	
	protected void generarCodificacion(){
		
		NodoArbolHuffman nodoAgregar = new NodoArbolHuffman();
		ArrayList<NodoArbolHuffman> listaaux = new ArrayList<NodoArbolHuffman>();
		ArrayList<NodoArbolHuffman> listMM = new ArrayList<NodoArbolHuffman>();
	
		for(NodoArbolHuffman nodo : listaNodos){
			listaaux.add(nodo);
		}

	
		//Verifico que haya mas de un nodo para ir obteniendo las codificaciones
		while (listaaux.size() > 1){
			listMM=this.obtenerMenoresFrecuencias(listaaux);

			//Creo un nuevo nodo que va a tener como hijos los dos simbolos con menores frecuencias
			nodoAgregar = new NodoArbolHuffman(listMM.get(0).getNombre()+"-"+listMM.get(1).getNombre(),listMM.get(0).getSimboloId()*listMM.get(1).getSimboloId(),listMM.get(0), listMM.get(1), listMM.get(0).getFrecuencia() + listMM.get(1).getFrecuencia());
			//Quito de la lista los dos simboles con menores frecuencias y agrego el nuevo nodo con la suma de estas
			//La identificacion que le di a los nodos intermedios fue "IdMayorFrecuencia-IdMenorFrecuencia"
			listaaux.remove(listMM.get(0));
			listaaux.remove(listMM.get(1));
			listaaux.add(nodoAgregar);
		}
		if(listaaux.size()==1){
			//En caso de que haya un solo simbolo no habria que hacer ninguna codificacion
			nodoAgregar = listaaux.get(0);
		}
		this.raiz = nodoAgregar;
	}
	
	private void getCod(NodoArbolHuffman nodo,String codigo, int longCodigo){
		//En caso de ser una hoja se agrega el simbolo con sus datos al archivo
		if(nodo.getHijoIzquierdo() == null && nodo.getHijoDerecho() == null){
			nodo.setCodificacion(codigo);
			nodo.setLongitud(longCodigo);
		}
			
			//Caso de ser un nodo intermedio se sigue expandiendo la busqueda en profundidad
			//Para la codificacion se tomo la rama izquierda como 1 y la derecha como 0

			if (nodo.getHijoIzquierdo() != null && nodo.getHijoDerecho() != null){
				longCodigo++;
				getCod(nodo.getHijoIzquierdo(),codigo+1,longCodigo);
				getCod(nodo.getHijoDerecho(),codigo+0,longCodigo);
				
				}
		
			if (nodo.getHijoIzquierdo() != null && nodo.getHijoDerecho() == null){
				longCodigo++;
				getCod(nodo.getHijoIzquierdo(),codigo+1,longCodigo);}
			if (nodo.getHijoIzquierdo() == null && nodo.getHijoDerecho() != null){
				longCodigo++;
				getCod(nodo.getHijoDerecho(),codigo+0,longCodigo);
				}
			
			}

	//Con este metodo voy a obtener la codificaciones del arbol de huffman 
	public void obtenerCodificacion(){
		if(this.raiz != null){
			String codigo="";
			int longCodigo = 0; 
			this.getCod(this.raiz,codigo,longCodigo);
		}
		}
	
	//Se va a imprimir un archivo con el siguiente formato por linea:
	//   IdentificacionSimbolo,Frecuencia,Codificacion,longitud
	public boolean imprimirCodificacion(){
		Archivo g = new Archivo();
			if(this.raiz != null){
				if(g.guardarComoTexto(listaNodos)){
					return true;
				}
			}
		return false;
		}
	
	public float calcularEntropia(){
		float entropia=0f;
		for(NodoArbolHuffman nodo : listaNodos){
			entropia += -nodo.getFrecuencia()*Math.log(nodo.getFrecuencia())/Math.log(2);
		}
		return entropia;
	}
	
	public float calcularLongitudMedia(){
		float longMedia=0f;
		for(NodoArbolHuffman nodo : listaNodos){
			longMedia += nodo.getFrecuencia()*nodo.getLongitud();
		}
		return longMedia;
	}
	
	
	public ArrayList <NodoArbolHuffman> getNodos(){
		return this.listaNodos;
	}
		
}