import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import ContulMeu from '../components/ContulMeu';
import api from '../services/api';
import { BrowserRouter } from 'react-router-dom';

jest.mock('../services/api');
const mockedApi = api as jest.Mocked<typeof api>;

describe('ContulMeu - teste simple', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const renderComponent = () => {
    render(
      <BrowserRouter>
        <ContulMeu />
      </BrowserRouter>
    );
  };

  it('afișează mesajul de încărcare inițial', () => {
    renderComponent();
    expect(screen.getByText(/Se încarcă datele contului/i)).toBeInTheDocument();
  });

  it('afișează datele contului dacă API-ul răspunde corect', async () => {
    mockedApi.get.mockResolvedValueOnce({
      data: {
        nume: 'Ion Popescu',
        email: 'ion@example.com',
        balanta: 123.45,
        tranzactii: [],
        autoexcludere: null,
      },
    });

    renderComponent();

    await waitFor(() => {
      expect(screen.getByText(/Ion Popescu/)).toBeInTheDocument();
      expect(screen.getByText(/ion@example.com/)).toBeInTheDocument();
      expect(screen.getByText(/123.45 RON/)).toBeInTheDocument();
      expect(screen.getByText(/Nu ai tranzacții înregistrate/i)).toBeInTheDocument();
    });
  });

  it('afișează mesaj de eroare dacă API-ul eșuează', async () => {
    mockedApi.get.mockRejectedValueOnce({
      response: { data: 'Eroare de server' },
    });

    renderComponent();

    expect(await screen.findByText(/A apărut o eroare/i)).toBeInTheDocument();
  });
});
