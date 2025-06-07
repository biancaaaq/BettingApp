export interface Cota {
  id: number;
  descriere: string;
  valoare: number;
  blocat: boolean;
}

export interface Utilizator {
  id: number;
  numeUtilizator: string;
  parola?: string;
  email?: string;
  rol?: string;
  activ?: boolean;
  dataCreare?: string;
}

export interface GrupMember {
  id: number;
  utilizator: Utilizator;
}

export interface Bilet {
  id: number;
  miza: number;
  cotaTotala: number;
  castigPotential: number;
  status: string;
  dataCreare: string;
  utilizator: Utilizator;
}

export interface GrupPrivat {
  id: number;
  nume: string;
  mizaComuna: number;
  status: 'IN_ASTEPTARE' | 'ACTIV' | 'FINALIZAT';
  admin?: Utilizator;
  membri?: GrupMember[];
  bilete?: Bilet[];
}