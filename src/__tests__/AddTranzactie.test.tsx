import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import AddTranzactie from '../components/AddTranzactie';
import api from '../services/api';

jest.mock('../services/api');
const mockedApi = api as jest.Mocked<typeof api>;

describe('AddTranzactie - teste simple', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('afișează formularul corect', () => {
    render(<AddTranzactie />);
    expect(screen.getAllByText(/Adaugă Tranzacție/i)[0]).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Adaugă Tranzacție/i })).toBeInTheDocument();
  });

it('trimite o tranzacție validă și afișează succes', async () => {
  mockedApi.post.mockResolvedValueOnce({ status: 200 });

  render(<AddTranzactie />);

  const inputs = screen.getAllByRole('spinbutton');
  fireEvent.change(inputs[0], { target: { value: '1' } });
  fireEvent.change(inputs[1], { target: { value: '100' } });

  fireEvent.click(screen.getByRole('button', { name: /Adaugă Tranzacție/i }));

  await waitFor(() => {
    expect(mockedApi.post).toHaveBeenCalledWith('/tranzactii', expect.objectContaining({
      utilizator: { id: 1 },
      tip: 'DEPOSIT',
      valoare: 100,
    }));
    expect(screen.getByText(/Tranzacție adăugată cu succes/i)).toBeInTheDocument();
  });
});


it('afișează mesaj de eroare dacă API-ul eșuează', async () => {
  mockedApi.post.mockRejectedValueOnce(new Error('Eroare'));

  render(<AddTranzactie />);

  const inputs = screen.getAllByRole('spinbutton');
  fireEvent.change(inputs[0], { target: { value: '1' } });
  fireEvent.change(inputs[1], { target: { value: '50' } });

  fireEvent.click(screen.getByRole('button', { name: /Adaugă Tranzacție/i }));

  expect(await screen.findByText(/Eroare la adăugarea tranzacției/i)).toBeInTheDocument();
});

});
