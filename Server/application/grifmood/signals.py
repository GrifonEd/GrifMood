from django.dispatch import receiver
from djoser.signals import user_registered

from .models import Profile


@receiver(user_registered, dispatch_uid="create_profile")
def create_profile(sender, user, request, **kwargs):
    """Создаём профиль пользователя при регистрации"""
    data = request.data

    Profile.objects.create(
        user=user,
        first_name=data.get("first_name", ""),
        second_name=data.get("second_name", ""),
        sex=data.get("sex", ""),
        age=data.get("age", ""),
    )
