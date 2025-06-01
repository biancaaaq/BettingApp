import React, { createContext, useContext, useState } from 'react';

export interface Cota {
  id: number;
  descriere: string;
  valoare: number;
  blocat: boolean;
}

interface BetSlipContextType {
  cote: Cota[];
  addCota: (cota: Cota) => void;
  removeCota: (id: number) => void;
  clearCote: () => void;
}

const BetSlipContext = createContext<BetSlipContextType | undefined>(undefined);

export const BetSlipProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [cote, setCote] = useState<Cota[]>([]);

  const addCota = (cota: Cota) => {
    if (cote.find(c => c.id === cota.id)) return; // evită duplicatele
    setCote([...cote, cota]);
  };

  const removeCota = (id: number) => {
    setCote(cote.filter(c => c.id !== id));
  };

  const clearCote = () => {
    setCote([]);
  };

  return (
    <BetSlipContext.Provider value={{ cote, addCota, removeCota, clearCote }}>
      {children}
    </BetSlipContext.Provider>
  );
};

export const useBetSlip = () => {
  const context = useContext(BetSlipContext);
  if (!context) throw new Error('useBetSlip must be used within BetSlipProvider');
  return context;
};
