import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import AddBalanta from '../components/AddBalanta';
import api from '../services/api';

jest.mock('../services/api');
const mockedApi = api as jest.Mocked<typeof api>;

describe('AddBalanta - teste simple', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('afișează formularul corect', () => {
    render(<AddBalanta />);
    expect(screen.getByRole('heading', { name: /Adaugă Balanță/i })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Adaugă Balanță/i })).toBeInTheDocument();
  });

  it('trimite o balanță validă și afișează succes', async () => {
    mockedApi.post.mockResolvedValueOnce({ status: 200 });

    render(<AddBalanta />);

    const inputs = screen.getAllByRole('spinbutton');
    fireEvent.change(inputs[0], { target: { value: '2' } }); // utilizatorId
    fireEvent.change(inputs[1], { target: { value: '500' } }); // suma

    fireEvent.click(screen.getByRole('button', { name: /Adaugă Balanță/i }));

    await waitFor(() => {
      expect(mockedApi.post).toHaveBeenCalledWith('/balanta', {
        utilizator: { id: 2 },
        suma: 500,
      });
      expect(screen.getByText(/Balanță adăugată cu succes/i)).toBeInTheDocument();
    });
  });

  it('afișează mesaj de eroare dacă API-ul eșuează', async () => {
    mockedApi.post.mockRejectedValueOnce(new Error('Eroare'));

    render(<AddBalanta />);

    const inputs = screen.getAllByRole('spinbutton');
    fireEvent.change(inputs[0], { target: { value: '3' } });
    fireEvent.change(inputs[1], { target: { value: '100' } });

    fireEvent.click(screen.getByRole('button', { name: /Adaugă Balanță/i }));

    expect(await screen.findByText(/Eroare la adăugarea balanței/i)).toBeInTheDocument();
  });
});
