import React, { createContext, useContext, useState } from 'react';

export interface Cota {
  id: number;
  descriere: string;
  valoare: number;
  blocat: boolean;
  idMeci: number;
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
    setCote(prev =>
      [
        ...prev.filter(c => c.idMeci !== cota.idMeci), // elimină cota precedentă din același meci
        cota,
      ]
    );
  };

  const removeCota = (id: number) => {
    setCote(prev => prev.filter(c => c.id !== id));
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
