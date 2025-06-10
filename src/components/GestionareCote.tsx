// components/admin/GestionareCote.tsx
import React, { useEffect, useState } from "react";
import api from "../services/api"; // <-- folosește același ca în AddCota

const GestionareCote = () => {
    interface Cota {    
        id: number;
        descriere: string;
        valoare: number;
        statusCota: string;
        blocat: boolean;
    }

    const [cote, setCote] = useState<Cota[]>([]);

    const fetchCote = async () => {
        const res = await api.get("/cote"); // ✅ folosim /cote, baza e în api.ts
        setCote(res.data);
    };

    const actualizeazaStatus = async (id: number, status: string): Promise<void> => {
        await api.put(`/cote/${id}/status`, null, {
            params: { statusNou: status },
        });
        fetchCote();
    };

    useEffect(() => {
        fetchCote();
    }, []);

    return (
        <div className="text-white p-4">
            <h2 className="text-xl font-bold mb-4">Gestionare Cote</h2>
            <table className="w-full table-auto text-sm">
                <thead>
                    <tr className="border-b border-gray-500">
                        <th>ID</th>
                        <th>Descriere</th>
                        <th>Valoare</th>
                        <th>Status</th>
                        <th>Blocată</th>
                        <th>Acțiuni</th>
                    </tr>
                </thead>
                <tbody>
                    {cote.map((cota) => (
                        <tr key={cota.id} className="text-center border-b border-gray-700">
                            <td>{cota.id}</td>
                            <td>{cota.descriere}</td>
                            <td>{cota.valoare}</td>
                            <td>{cota.statusCota}</td>
                            <td>{cota.blocat ? "DA" : "NU"}</td>
                            <td className="space-x-2">
                                <button
                                    className="bg-green-500 px-2 py-1 rounded"
                                    onClick={() => actualizeazaStatus(cota.id, "castigator")}
                                >
                                    Castig
                                </button>
                                <button
                                    className="bg-red-500 px-2 py-1 rounded"
                                    onClick={() => actualizeazaStatus(cota.id, "pierzator")}
                                >
                                    Pierde
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default GestionareCote;
