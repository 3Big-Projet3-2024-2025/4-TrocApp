export interface Rating {
    id?: number;
    numberStars: number;
    comment: string;
    poster: { id: number; firstName: string; lastName: string };
    receiver: { id: number; firstName: string; lastName: string };
  }