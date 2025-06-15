import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import AddCota from '../components/AddCota';
import api from '../services/api';

// Mock API
jest.mock('../services/api');

describe('AddCota component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const getInputByLabelText = (labelText: string): HTMLInputElement => {
  const labels = screen.getAllByText((content, element) => {
    return (
      element?.tagName?.toLowerCase() === 'label' &&
      content.toLowerCase().includes(labelText.toLowerCase())
    );
  });

  const label = labels[0] as HTMLElement | undefined;
  if (!label) {
    throw new Error(`Label not found: ${labelText}`);
  }

  const input = label.nextElementSibling as HTMLInputElement | null;
  if (!input) {
    throw new Error(`No input found for label: ${label.textContent}`);
  }

  return input;
};


  it('afișează formularul cu toate câmpurile', () => {
    render(<AddCota />);

    expect(screen.getByText(/ID Meci/i)).toBeInTheDocument();
    expect(screen.getByText(/Descriere/i)).toBeInTheDocument();
    expect(screen.getByText(/Valoare/i)).toBeInTheDocument();
    expect(screen.getByText(/Blocat/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Adaugă Cotă/i })).toBeInTheDocument();
  });

  it('trimite corect datele și afișează mesaj de succes', async () => {
    (api.post as jest.Mock).mockResolvedValueOnce({ status: 200 });

    render(<AddCota />);

    fireEvent.change(getInputByLabelText('ID Meci'), { target: { value: '101' } });
    fireEvent.change(getInputByLabelText('Descriere'), { target: { value: 'Echipa 1 câștigă' } });
    fireEvent.change(getInputByLabelText('Valoare'), { target: { value: '2.5' } });
    fireEvent.click(getInputByLabelText('Blocat')); // bifează checkbox-ul

    fireEvent.click(screen.getByRole('button', { name: /Adaugă Cotă/i }));

    await waitFor(() => {
      expect(api.post).toHaveBeenCalledWith('/cote', {
        meci: { id: 101 },
        descriere: 'Echipa 1 câștigă',
        valoare: 2.5,
        blocat: true,
      });
      expect(screen.getByText(/Cotă adăugată cu succes!/i)).toBeInTheDocument();
    });
  });

  it('afișează mesaj de eroare dacă API-ul eșuează', async () => {
    (api.post as jest.Mock).mockRejectedValueOnce(new Error('Network error'));

    render(<AddCota />);

    fireEvent.change(getInputByLabelText('ID Meci'), { target: { value: '102' } });
    fireEvent.change(getInputByLabelText('Descriere'), { target: { value: 'Egal' } });
    fireEvent.change(getInputByLabelText('Valoare'), { target: { value: '3.1' } });

    fireEvent.click(screen.getByRole('button', { name: /Adaugă Cotă/i }));

    await waitFor(() => {
      expect(api.post).toHaveBeenCalled();
      expect(screen.getByText(/Eroare la adăugarea cotei/i)).toBeInTheDocument();
    });
  });
});
